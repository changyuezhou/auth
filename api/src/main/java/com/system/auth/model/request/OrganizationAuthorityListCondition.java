package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class OrganizationAuthorityListCondition {
    private String organizationId;
    private String organizationName;

    @NotNull(message = "platform id must not be null")
    private String platformId;

    private String authId;
    private String authName;

    private String createUserName;
    private String createUserId;

    @NotNull(message = "pageNum can not be null")
    private Integer pageNum;
    @NotNull(message = "pageSize can not be null")
    private Integer pageSize;

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
