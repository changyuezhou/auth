package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class RoleKey {
    @NotNull(message = "roleId must not be null")
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleKey() {
        this.roleId = null;
    }

    public RoleKey(String roleId) {
        this.roleId = roleId;
    }
}
