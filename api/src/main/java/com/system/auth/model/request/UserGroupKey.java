package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class UserGroupKey {
    @NotNull(message = "user id must not be null")
    private String userId;

    @NotNull(message = "group id must not be null")
    private String groupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}