package com.system.auth.model;

public class UserCustomArchKey {
    private String userId;

    private String customArchId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCustomArchId() {
        return customArchId;
    }

    public void setCustomArchId(String customArchId) {
        this.customArchId = customArchId == null ? null : customArchId.trim();
    }
}