package com.system.auth.model.ext;

import com.system.auth.model.Organization;

public class OrganizationView extends Organization {
    private String createUserName;
    private String platformName;
    private String organizationFName;

    public String getCreateUserName() {
        return createUserName;
    }
    public String getPlatformName() { return platformName; }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public void setPlatformName(String platformName) { this.platformName = platformName; }

    public String getOrganizationFName() {
        return organizationFName;
    }

    public void setOrganizationFName(String organizationFName) {
        this.organizationFName = organizationFName;
    }
}
