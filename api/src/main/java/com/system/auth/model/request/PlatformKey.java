package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class PlatformKey {
    @NotNull(message = "platformId must not be null")
    private String platformId;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public PlatformKey() {
        this.platformId = null;
    }

    public PlatformKey(String platformId) {
        this.platformId = platformId;
    }
}
