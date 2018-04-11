package com.system.auth.model.ext;

import com.system.auth.model.System;

public class SystemView extends System {
    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
