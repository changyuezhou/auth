package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class RefreshTokenRequest {
    @NotNull(message = "platform id must not be null")
    private String platformId;

    @NotNull(message = "open id must not be null")
    private String openId;

    @NotNull(message = "refresh token must not be null")
    private String refreshToken;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }
}
