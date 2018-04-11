package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class GroupKey {
    @NotNull(message = "groupId must not be null")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupKey() {
        this.groupId = null;
    }

    public GroupKey(String groupId) {
        this.groupId = groupId;
    }
}
