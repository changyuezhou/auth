package com.system.auth.bean;

import java.util.List;

public class QueryListMessage<T> extends OperationMessage {
    private Integer pageSize;
    private Integer pageNum;
    private Long totalNum;

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

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public QueryListMessage() {
    }

    public QueryListMessage(Integer code, String msg, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
