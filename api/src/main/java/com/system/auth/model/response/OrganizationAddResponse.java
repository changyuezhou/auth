package com.system.auth.model.response;

public class OrganizationAddResponse {
    private String organizationId;
    private String organizationName;

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public OrganizationAddResponse() {
        this.organizationId = "";
        this.organizationName = "";
    }

    public OrganizationAddResponse(String organizationId, String organizationName) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }
}
