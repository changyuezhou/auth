package com.system.auth.model;

import javax.validation.constraints.NotNull;

public class Platform {
    private String platformId;

    @NotNull(message = "platform name must not be null")
    private String platformName;

    @NotNull(message = "secret key must not be null")
    private String secretKey;

    @NotNull(message = "system id must not be null")
    private String systemId;

    @NotNull(message = "platform domain must not be null")
    private String platformDomain;

    private String description;

    private String createUserId;

    private Long updateTime;

    private Long createTime;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getPlatformDomain() {
        return platformDomain;
    }

    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain == null ? null : platformDomain.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}