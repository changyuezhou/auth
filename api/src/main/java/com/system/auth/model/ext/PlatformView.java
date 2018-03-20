package com.system.auth.model.ext;

import com.system.auth.model.Platform;

public class PlatformView extends Platform {
    private String createUserName;
    private String systemName;

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
}
