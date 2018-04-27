package com.system.auth.model.ext;

import java.util.List;

public class UserMenuAuthorityView {
    private String authId;
    private String authName;

    private String url;

    private String authFId;
    private String authFName;

    private List<UserMenuAuthorityView> children;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthFName() {
        return authFName;
    }

    public void setAuthFName(String authFName) {
        this.authFName = authFName;
    }

    public String getAuthFId() {
        return authFId;
    }

    public void setAuthFId(String authFId) {
        this.authFId = authFId;
    }

    public void setChildren(List<UserMenuAuthorityView> children) {
        this.children = children;
    }

    public List<UserMenuAuthorityView> getChildren() {
        return children;
    }
}
