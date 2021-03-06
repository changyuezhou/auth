package com.system.auth.model;

import com.system.auth.model.request.RoleAuthorityKey;

public class RoleAuthority extends RoleAuthorityKey {
    private String createUserId;

    private Long updateTime;

    private Long createTime;

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