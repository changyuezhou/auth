package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrganizationAuthorityBulk {
    @NotNull(message = "organization id must not be null")
    private String organizationId;

    private Long updateTime;

    private Long createTime;

    public Long getCreateTime() {
        return createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    private List<String> authIds;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public List<String> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<String> authIds) {
        this.authIds = authIds;
    }
}
