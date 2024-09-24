package com.backend.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    GOD("God", 100),
    ADMIN("Admin", 90),
    MODERATOR("Moderator", 80),
    OWNER("Owner", 70),
    MANAGER("Manager", 60),
    VOLUNTEER("Volunteer", 50),
    USER("User", 10);

    private final String role;
    private final int weight;

    UserAuthority(String role, int weight) {
        this.role = role;
        this.weight = weight;
    }

    public String getRole() {
        return role;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}
