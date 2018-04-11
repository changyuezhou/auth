package com.system.auth.model.provider;

import com.system.auth.model.request.RoleAuthorityBulk;

public class RoleAuthorityBulkInsert extends RoleAuthorityBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
