package com.system.auth.model;

public class Session {
    private Integer id;

    private String platformId;

    private Integer authTokenCreateTime;

    private Integer authTokenExpire;

    private String authToken;

    private Boolean authTokenUsed;

    private String openId;

    private String userId;

    private String code;

    private Integer codeCreateTime;

    private Integer codeExpire;

    private Boolean codeUsed;

    private String accessToken;

    private Integer accessTokenCreateTime;

    private Integer accessTokenExpire;

    private Boolean accessTokenUsed;

    private String refreshToken;

    private Integer refreshTokenCreateTime;

    private Integer refreshTokenExpire;

    private Boolean refreshTokenUsed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public Integer getAuthTokenCreateTime() {
        return authTokenCreateTime;
    }

    public void setAuthTokenCreateTime(Integer authTokenCreateTime) {
        this.authTokenCreateTime = authTokenCreateTime;
    }

    public Integer getAuthTokenExpire() {
        return authTokenExpire;
    }

    public void setAuthTokenExpire(Integer authTokenExpire) {
        this.authTokenExpire = authTokenExpire;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken == null ? null : authToken.trim();
    }

    public Boolean getAuthTokenUsed() {
        return authTokenUsed;
    }

    public void setAuthTokenUsed(Boolean authTokenUsed) {
        this.authTokenUsed = authTokenUsed;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getCodeCreateTime() {
        return codeCreateTime;
    }

    public void setCodeCreateTime(Integer codeCreateTime) {
        this.codeCreateTime = codeCreateTime;
    }

    public Integer getCodeExpire() {
        return codeExpire;
    }

    public void setCodeExpire(Integer codeExpire) {
        this.codeExpire = codeExpire;
    }

    public Boolean getCodeUsed() {
        return codeUsed;
    }

    public void setCodeUsed(Boolean codeUsed) {
        this.codeUsed = codeUsed;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Integer getAccessTokenCreateTime() {
        return accessTokenCreateTime;
    }

    public void setAccessTokenCreateTime(Integer accessTokenCreateTime) {
        this.accessTokenCreateTime = accessTokenCreateTime;
    }

    public Integer getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Integer accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public Boolean getAccessTokenUsed() {
        return accessTokenUsed;
    }

    public void setAccessTokenUsed(Boolean accessTokenUsed) {
        this.accessTokenUsed = accessTokenUsed;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public Integer getRefreshTokenCreateTime() {
        return refreshTokenCreateTime;
    }

    public void setRefreshTokenCreateTime(Integer refreshTokenCreateTime) {
        this.refreshTokenCreateTime = refreshTokenCreateTime;
    }

    public Integer getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(Integer refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public Boolean getRefreshTokenUsed() {
        return refreshTokenUsed;
    }

    public void setRefreshTokenUsed(Boolean refreshTokenUsed) {
        this.refreshTokenUsed = refreshTokenUsed;
    }
}