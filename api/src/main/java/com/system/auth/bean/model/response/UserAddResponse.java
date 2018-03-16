package com.system.auth.bean.model.response;

public class UserAddResponse {
    private String userId;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public UserAddResponse() {
        this.userId = "";
        this.userName = "";
    }

    public UserAddResponse(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
