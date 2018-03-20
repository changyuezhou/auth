package com.system.auth.model.provider;

import com.system.auth.model.request.UserGroupsBulk;

public class UserGroupBulkInsert extends UserGroupsBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
