package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class OrganizationKey {
    @NotNull(message = "organizationId must not be null")
    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public OrganizationKey() {
        this.organizationId = null;
    }

    public OrganizationKey(String organizationId) {
        this.organizationId = organizationId;
    }
}
