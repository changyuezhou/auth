package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PlatformBulk {
    @NotNull(message = "platform id list must not be null")
    private List<String> platformIds;

    public List<String> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(List<String> platformIds) {
        this.platformIds = platformIds;
    }
}
