package com.system.auth.model;

public class CustomArchAuthorityKey {
    private String customArchId;

    private String authId;

    public String getCustomArchId() {
        return customArchId;
    }

    public void setCustomArchId(String customArchId) {
        this.customArchId = customArchId == null ? null : customArchId.trim();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }
}