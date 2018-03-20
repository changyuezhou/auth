package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class SystemKey {
    @NotNull(message = "systemId must not be null")
    private String systemId;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public SystemKey() {
        this.systemId = null;
    }

    public SystemKey(String systemId) {
        this.systemId = systemId;
    }
}
