package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RoleBulk {
    @NotNull(message = "role id list must not be null")
    private List<String> roleIds;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
