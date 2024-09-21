package com.backend.models.enums;

public enum UserRole {
    OWNER("Owner"),
    MANAGER("Manager"),
    VOLUNTEER("Volunteer"),
    ADMIN("Admin"),
    GOD("God");
    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
