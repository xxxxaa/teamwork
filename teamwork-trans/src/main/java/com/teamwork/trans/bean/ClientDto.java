package com.teamwork.trans.bean;

/**
 * @author yan
 * @Description:
 * @date 2017/3/7
 */
public class ClientDto {

    private long id;
    private int rid;
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
