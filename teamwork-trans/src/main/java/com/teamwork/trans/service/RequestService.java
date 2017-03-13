package com.teamwork.trans.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.teamwork.trans.bean.Client;
import com.teamwork.trans.bean.ClientDto;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;


/**
 * @author yan
 * @Description:
 * @date 2017/3/6
 */
public class RequestService {

    public static Client clientRegister(String request){
        ObjectMapper objectMapper = new ObjectMapper();
        String res = new String(Base64.decodeBase64(request));

        ClientDto clientDto = null;
        try {
            clientDto = objectMapper.readValue(res, ClientDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setRoomId(clientDto.getRid());
        return client;
    }

    public static void main(String[] args) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(2);
        clientDto.setRid(2);
        clientDto.setToken("jdfkljfkjsafljsfljasdjfjfasjflsjflsjdef");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println(objectMapper.writeValueAsString(clientDto));
            String en = new String(Base64.encodeBase64(objectMapper.writeValueAsString(clientDto).getBytes()));
            System.out.println(en);


            System.out.println(new String(Base64.decodeBase64(en.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
