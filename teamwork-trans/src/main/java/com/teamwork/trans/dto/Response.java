package com.teamwork.trans.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 * @Description:
 * @date 2017/3/6
 */
public class Response {
    private int error_code; // 成功时 0 ,如果大于 0 则表示则显示error_msg
    private String error_msg;
    private Map<String,Object> data;

    public Response() {
        data = new HashMap<>();
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Response(int error_code, String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }
}
