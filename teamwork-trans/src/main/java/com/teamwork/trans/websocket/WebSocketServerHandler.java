package com.teamwork.trans.websocket;

import com.teamwork.trans.bean.Client;
import com.teamwork.trans.dto.Response;
import com.teamwork.trans.service.MessageService;
import com.teamwork.trans.service.RequestService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author yan
 * @Description:
 * @date 2017/3/6
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final String WEBSOCKET_PATH = "/websocket";

    private static Map<Integer, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    private static final String HTTP_REQUEST_STRING = "request";

    private Client client = null;

    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
        } else if(msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame)msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest){
        if(fullHttpRequest.decoderResult().isFailure()){
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        if(fullHttpRequest.method() != GET){
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }
        if("/favicon.ico".equals(fullHttpRequest.uri()) || ("/".equals(fullHttpRequest.uri()))){
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();

        if(parameters.size() == 0 || !parameters.containsKey(HTTP_REQUEST_STRING)){
            System.err.printf(HTTP_REQUEST_STRING + "参数不可缺省");
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        client = RequestService.clientRegister(parameters.get(HTTP_REQUEST_STRING).get(0));
        if(client.getRoomId() == 0){
            System.err.printf("房间号不可缺省");
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        // 房间列表中如果不存在则为该频道,则新增一个频道 ChannelGroup
        if(!channelGroupMap.containsKey(client.getRoomId())){
            channelGroupMap.put(client.getRoomId(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
        // 确定有房间号,才将客户端加入到频道中
        channelGroupMap.get(client.getRoomId()).add(channelHandlerContext.channel());

        //handshake
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(fullHttpRequest), null, true);
        webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);

        if(webSocketServerHandshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
        } else {
            ChannelFuture channelFuture = webSocketServerHandshaker.handshake(channelHandlerContext.channel(), fullHttpRequest);

            //握手成功后,业务逻辑
            if(channelFuture.isSuccess()){
                if(client.getId() == 0){
                    System.out.println(channelHandlerContext.channel() + " 游客");
                    return;
                }
            }
        }
    }


    private void broadcast(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame){
        if(client.getId() == 0){
            Response response = new Response(1001, "没有登录聊天室");
            String msg = response.toString();
            channelHandlerContext.channel().write(new TextWebSocketFrame(msg));
            return;
        }

        String request = ((TextWebSocketFrame)webSocketFrame).text();
        System.out.println("收到 " + channelHandlerContext.channel() + request);

        Response response = MessageService.sendMessage(client, request);
        String msg = response.toString();
        if(channelGroupMap.containsKey(client.getRoomId())){
            channelGroupMap.get(client.getRoomId()).writeAndFlush(new TextWebSocketFrame(msg));
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame){
        if(webSocketFrame instanceof CloseWebSocketFrame){
            webSocketServerHandshaker.close(channelHandlerContext.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
        }

        if(webSocketFrame instanceof PingWebSocketFrame){
            channelHandlerContext.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }

        if(!(webSocketFrame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame types not supported", webSocketFrame.getClass().getName()));
        }
        broadcast(channelHandlerContext, webSocketFrame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("收到握手请求" + incoming.remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(client != null && channelGroupMap.containsKey(client.getRoomId())){
            channelGroupMap.get(client.getRoomId()).remove(ctx.channel());
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse){
        if(fullHttpResponse.status().code() != 200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(fullHttpResponse.status().toString(), CharsetUtil.UTF_8);
            fullHttpResponse.content().writeBytes(byteBuf);
            byteBuf.release();
            HttpHeaderUtil.setContentLength(fullHttpResponse, fullHttpResponse.content().readableBytes());
        }

        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(fullHttpResponse);
        if(!HttpHeaderUtil.isKeepAlive(fullHttpRequest) || fullHttpResponse.status().code() != 200){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(FullHttpRequest fullHttpRequest){
        String location = fullHttpRequest.headers().get(HOST) + WEBSOCKET_PATH;
        return "ws://" + location;
    }
}
