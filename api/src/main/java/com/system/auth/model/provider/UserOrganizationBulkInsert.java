package com.system.auth.model.provider;

import com.system.auth.model.request.UserOrganizationBulk;

public class UserOrganizationBulkInsert extends UserOrganizationBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
