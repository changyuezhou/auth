package com.system.auth.model;

public class Organization {
    private String organizationId;

    private String organizationName;

    private String organizationFId;

    private String platformId;

    private Integer organizationLevel;

    private String organizationFTree;

    private String description;

    private String createUserId;

    private Long updateTime;

    private Long createTime;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public String getOrganizationFId() {
        return organizationFId;
    }

    public void setOrganizationFId(String organizationFId) {
        this.organizationFId = organizationFId == null ? null : organizationFId.trim();
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public Integer getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public String getOrganizationFTree() {
        return organizationFTree;
    }

    public void setOrganizationFTree(String organizationFTree) {
        this.organizationFTree = organizationFTree == null ? null : organizationFTree.trim();
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