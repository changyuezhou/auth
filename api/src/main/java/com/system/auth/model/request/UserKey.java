package com.system.auth.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserKey {

    @NotNull(message = "userId must not be null")
    @Length(min = 16, max = 128, message = "userId must be string and length is between 16,128")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserKey() {
        this.userId = null;
    }

    public UserKey(String userId) {
        this.userId = userId;
    }
}
