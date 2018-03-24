package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RoleAuthorityBulk {
    @NotNull(message = "role id must not be null")
    private String roleId;

    private List<String> authIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public List<String> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<String> authIds) {
        this.authIds = authIds;
    }
}
