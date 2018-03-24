package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SystemBulk {
    @NotNull(message = "system id list must not be null")
    private List<String> systemIds;

    public List<String> getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(List<String> systemIds) {
        this.systemIds = systemIds;
    }
}
