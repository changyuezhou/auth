package com.system.auth.model.ext;

import com.system.auth.model.Authority;

public class AuthorityView extends Authority {
    private String systemName;
    private String createUserName;
    private String authFName;

    public String getCreateUserName() {
        return createUserName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setAuthFName(String authFName) {
        this.authFName = authFName;
    }

    public String getAuthFName() {
        return authFName;
    }
}
