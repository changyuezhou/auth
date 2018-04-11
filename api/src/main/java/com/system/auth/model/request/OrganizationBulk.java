package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrganizationBulk {
    @NotNull(message = "organization id must not be null")
    private List<String> organizationIds;

    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }
}
