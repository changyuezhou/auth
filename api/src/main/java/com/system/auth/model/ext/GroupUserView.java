package com.system.auth.model.ext;

import java.util.List;

public class GroupUserView {
    private String groupId;
    private String groupName;

    List<UserView> users;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public List<UserView> getUsers() {
        return users;
    }

    public void setUsers(List<UserView> users) {
        this.users = users;
    }
}

