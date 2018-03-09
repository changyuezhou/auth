package com.system.auth.model;

public class Authority {
    private Integer authId;

    private String authName;

    private Integer authFId;

    private Integer systemId;

    private Integer authLevel;

    private String authFTree;

    private String description;

    private Integer createUserId;

    private Long updateTime;

    private Long createTime;

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public Integer getAuthFId() {
        return authFId;
    }

    public void setAuthFId(Integer authFId) {
        this.authFId = authFId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public String getAuthFTree() {
        return authFTree;
    }

    public void setAuthFTree(String authFTree) {
        this.authFTree = authFTree == null ? null : authFTree.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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