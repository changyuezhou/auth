package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserGroupsBulk {
    @NotNull(message = "user id must not be null")
    private String userId;

    private List<String> groupIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }
}
