package com.system.auth.model.response;

public class PlatformAddResponse {
    private String platformId;
    private String platformName;

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public PlatformAddResponse() {
        this.platformId = "";
        this.platformName = "";
    }

    public PlatformAddResponse(String platformId, String platformName) {
        this.platformId = platformId;
        this.platformName = platformName;
    }
}
