package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class SystemListCondition {
    private String systemId;

    private String systemName;

    private String createUserName;
    private String createUserId;

    @NotNull(message = "pageNum can not be null")
    private Integer pageNum;
    @NotNull(message = "pageSize can not be null")
    private Integer pageSize;

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }
}
