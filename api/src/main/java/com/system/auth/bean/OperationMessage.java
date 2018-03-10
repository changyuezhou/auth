package com.system.auth.bean;

public class OperationMessage implements MessageInterface {
    private int code;
    private String msg;

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

    public OperationMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
