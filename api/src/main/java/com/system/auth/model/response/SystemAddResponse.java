package com.system.auth.model.response;

public class SystemAddResponse {
    private String systemId;
    private String systemName;

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemId() {
        return systemId;
    }

    public SystemAddResponse() {
        this.systemId = "";
        this.systemName = "";
    }

    public SystemAddResponse(String systemId, String systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
    }
}
