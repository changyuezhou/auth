package com.system.auth.bean;

import com.system.auth.model.User;

import java.util.List;

public class QueryUserListMessage extends OperationMessage {
    private int pageSize;
    private int pageNum;
    private Long totalNum;

    private List<User> data;

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

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    public QueryUserListMessage(int code, String msg, List<User> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
