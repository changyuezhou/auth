package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class ApplyAuthToken {
    @NotNull(message = "platform id must not be null")
    private String platformId;
    @NotNull(message = "password id must not be null")
    private String password;
    @NotNull(message = "random id must not be null")
    private String random;

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getPassword() {
        return password;
    }

    public String getRandom() {
        return random;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRandom(String random) {
        this.random = random;
    }
}
