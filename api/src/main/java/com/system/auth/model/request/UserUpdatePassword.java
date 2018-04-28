package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class UserUpdatePassword {
    private String userId;

    @NotNull(message = "oldPwd must not null")
    private String oldPwd;

    @NotNull(message = "newPwd must not null")
    private String newPwd;


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }
}
