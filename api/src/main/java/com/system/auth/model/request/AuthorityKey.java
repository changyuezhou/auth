package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class AuthorityKey {
    @NotNull(message = "authId must not be null")
    private String authId;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public AuthorityKey() {
        this.authId = null;
    }

    public AuthorityKey(String authId) {
        this.authId = authId;
    }
}
