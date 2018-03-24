package com.system.auth.model.request;

import javax.validation.constraints.NotNull;

public class GroupAuthorityKey {
    @NotNull(message = "group id must not be null")
    private String groupId;

    @NotNull(message = "authority id must not be null")
    private String authId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }
}