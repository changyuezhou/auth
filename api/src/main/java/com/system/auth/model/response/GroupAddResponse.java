package com.system.auth.model.response;

public class GroupAddResponse {
    private String groupId;
    private String groupName;

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public GroupAddResponse() {
        this.groupId = "";
        this.groupName = "";
    }

    public GroupAddResponse(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
