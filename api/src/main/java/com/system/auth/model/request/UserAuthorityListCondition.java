package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class UserAuthorityListCondition {
    private String platformId;
    private String platformName;

    private String userId;
    private String userName;

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

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getPlatformName() {
        return platformName;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthId() {
        return authId;
    }
}
