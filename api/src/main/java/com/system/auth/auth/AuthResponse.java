package com.system.auth.auth;

public class AuthResponse<T> {
    public Integer code;
    public String msg;
    private T data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
