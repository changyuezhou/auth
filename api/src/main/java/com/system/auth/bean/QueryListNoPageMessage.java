package com.system.auth.bean;

import java.util.List;

public class QueryListNoPageMessage<T> extends OperationMessage {
    private List<T> data;

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

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public QueryListNoPageMessage() {
    }

    public QueryListNoPageMessage(Integer code, String msg, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
