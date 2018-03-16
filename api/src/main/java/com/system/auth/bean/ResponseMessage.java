package com.system.auth.bean;

import com.system.auth.model.ext.UserView;

public class ResponseMessage<T> extends OperationMessage {
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

    public ResponseMessage() {
    }

    public ResponseMessage(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
