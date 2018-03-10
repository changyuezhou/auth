package com.system.auth.bean;

import com.system.auth.model.User;

public class QueryUserMessage implements MessageInterface {
    private int code;
    private String msg;
    private User data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public QueryUserMessage(int code, String msg, User data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
