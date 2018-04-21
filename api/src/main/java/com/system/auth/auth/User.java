package com.system.auth.auth;

public class User {
    private String userId;
    private String userName;
    private String contactName;
    private String mobileNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contackName) {
        this.contactName = contackName;
    }
}
