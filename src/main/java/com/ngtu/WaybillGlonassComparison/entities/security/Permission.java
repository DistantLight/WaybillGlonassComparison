package com.ngtu.WaybillGlonassComparison.entities.security;

public enum Permission {
    REPORTS_READ("reports:read"),
    REPORTS_WRITE("reports:write"),
    USERS_ADD("users:add");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
