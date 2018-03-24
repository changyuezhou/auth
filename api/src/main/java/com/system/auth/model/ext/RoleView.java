package com.system.auth.model.ext;

import com.system.auth.model.Role;

public class RoleView extends Role {
    private String createUserName;
    private String platformName;

    public String getCreateUserName() {
        return createUserName;
    }
    public String getPlatformName() { return platformName; }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public void setPlatformName(String platformName) { this.platformName = platformName; }
}
