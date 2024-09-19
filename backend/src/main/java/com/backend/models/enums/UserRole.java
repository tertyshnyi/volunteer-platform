package com.backend.models.enums;

public enum UserRole {
    OWNER("Owner organization"),
    MANAGER("Manager organization");
    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
