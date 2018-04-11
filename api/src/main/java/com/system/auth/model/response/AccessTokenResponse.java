package com.system.auth.model.response;

public class AccessTokenResponse {
    private String platformId;
    private String openId;
    private String userId;
    private String userName;
    private Integer accessTokenExpire;
    private String accessToken;
    private Integer refreshTokenExpire;
    private String refreshToken;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Integer accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public Integer getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setRefreshTokenExpire(Integer refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
