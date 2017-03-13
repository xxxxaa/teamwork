package com.teamwork.trans.service;

import com.teamwork.trans.bean.Client;
import com.teamwork.trans.dto.Response;

/**
 * @author yan
 * @Description:
 * @date 2017/3/6
 */
public class MessageService {

    public static Response sendMessage(Client client, String message) {
        Response res = new Response();
        res.getData().put("id", client.getId());
        res.getData().put("message", message);
        res.getData().put("ts", System.currentTimeMillis());// 返回毫秒数
        return res;
    }
}
