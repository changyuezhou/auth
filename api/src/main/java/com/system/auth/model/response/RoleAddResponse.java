package com.system.auth.model.response;

public class RoleAddResponse {
    private String roleId;
    private String roleName;

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public RoleAddResponse() {
        this.roleId = "";
        this.roleName = "";
    }

    public RoleAddResponse(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
