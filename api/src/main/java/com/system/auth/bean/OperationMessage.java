package com.system.auth.bean;

public class OperationMessage {
    public Integer code;
    public String msg;

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

    public OperationMessage() {
        this.code = 0;
        this.msg = "";
    }

    public OperationMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
