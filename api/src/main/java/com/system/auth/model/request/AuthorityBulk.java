package com.system.auth.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AuthorityBulk {
    @NotNull(message = "authority id list must not be null")
    private List<String> authIds;

    public List<String> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<String> authIds) {
        this.authIds = authIds;
    }
}
