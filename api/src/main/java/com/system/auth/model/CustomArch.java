package com.system.auth.model;

public class CustomArch {
    private String customArchId;

    private String customArchName;

    private String customArchFId;

    private String platformId;

    private Integer flag;

    private Integer customArchLevel;

    private String customArchFTree;

    private String description;

    private String createUserId;

    private Long updateTime;

    private Long createTime;

    public String getCustomArchId() {
        return customArchId;
    }

    public void setCustomArchId(String customArchId) {
        this.customArchId = customArchId == null ? null : customArchId.trim();
    }

    public String getCustomArchName() {
        return customArchName;
    }

    public void setCustomArchName(String customArchName) {
        this.customArchName = customArchName == null ? null : customArchName.trim();
    }

    public String getCustomArchFId() {
        return customArchFId;
    }

    public void setCustomArchFId(String customArchFId) {
        this.customArchFId = customArchFId == null ? null : customArchFId.trim();
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCustomArchLevel() {
        return customArchLevel;
    }

    public void setCustomArchLevel(Integer customArchLevel) {
        this.customArchLevel = customArchLevel;
    }

    public String getCustomArchFTree() {
        return customArchFTree;
    }

    public void setCustomArchFTree(String customArchFTree) {
        this.customArchFTree = customArchFTree == null ? null : customArchFTree.trim();
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