package com.system.auth.bean;

public class QueryMessage<T> extends OperationMessage {
    private T data;

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

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public QueryMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
