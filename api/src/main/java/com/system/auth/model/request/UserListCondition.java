package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class UserListCondition {
    private String userId;

    private String userName;

    private Integer status;

    private String mobileNumber;

    private String contactName;

    @NotNull(message = "pageNum can not be null")
    private Integer pageNum;
    @NotNull(message = "pageSize can not be null")
    private Integer pageSize;

    public String getUserId() {
        return userId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getContactName() {
        return contactName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
