package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class AuthorizationCode {
    @NotNull(message = "platform id must not be null")
    private String platformId;

    @NotNull(message = "password must not be null")
    private String password;

    @NotNull(message = "user name must not be null")
    private String userName;

    @NotNull(message = "auth token must not be null")
    private String authToken;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
