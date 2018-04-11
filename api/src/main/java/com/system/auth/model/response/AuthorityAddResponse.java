package com.system.auth.model.response;

public class AuthorityAddResponse {
    private String authId;
    private String authName;


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public AuthorityAddResponse() {
        this.authId = "";
        this.authName = "";
    }

    public AuthorityAddResponse(String authId, String authName) {
        this.authId = authId;
        this.authName = authName;
    }
}
