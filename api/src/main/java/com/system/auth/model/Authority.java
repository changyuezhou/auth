package com.system.auth.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class Authority {
    private String authId;

    @ApiModelProperty(value="权限名称", example="系统管理", required=true)
    @NotNull(message = "authName must not be null")
    private String authName;

    private String authFId;

    private String url;

    @NotNull(message = "systemId must not be null")
    private String systemId;

    private Integer authLevel;

    private String authFTree;

    private String description;

    private String createUserId;

    private Long updateTime;

    private Long createTime;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getAuthFId() {
        return authFId;
    }

    public void setAuthFId(String authFId) {
        this.authFId = authFId == null ? null : authFId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
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