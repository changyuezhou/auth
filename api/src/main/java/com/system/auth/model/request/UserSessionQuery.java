package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class UserSessionQuery {
    @NotNull(message = "platform id must not null")
    private String platformId;

    @NotNull(message = "open id must not null")
    private String openId;

    @NotNull(message = "access token must not be null")
    private String accessToken;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
