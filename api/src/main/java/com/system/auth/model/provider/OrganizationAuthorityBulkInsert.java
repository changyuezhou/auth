package com.system.auth.model.provider;

import com.system.auth.model.request.OrganizationAuthorityBulk;

public class OrganizationAuthorityBulkInsert extends OrganizationAuthorityBulk {
    private String createUserId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }
}
