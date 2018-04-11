package com.system.auth.model.provider;

import com.system.auth.model.request.UserRoleBulk;

public class UserRoleBulkInsert extends UserRoleBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
