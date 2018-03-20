package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GroupBulk {
    @NotNull(message = "user id must not be null")
    private List<String> groupIds;

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }
}
