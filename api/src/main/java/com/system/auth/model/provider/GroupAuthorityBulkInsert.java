package com.system.auth.model.provider;

import com.system.auth.model.request.GroupAuthorityBulk;

public class GroupAuthorityBulkInsert extends GroupAuthorityBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
