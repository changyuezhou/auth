package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class AccessTokenRequest {
    @NotNull(message = "platform id must not be null")
    private String platformId;

    @NotNull(message = "open id must not be null")
    private String openId;

    @NotNull(message = "code id must not be null")
    private String code;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }
}
