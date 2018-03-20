package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserBulk {
    @NotNull(message = "user id must not be null")
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
