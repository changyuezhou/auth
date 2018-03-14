package com.system.auth.model.ext;

import com.system.auth.model.User;

public class UserView extends User {
    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
