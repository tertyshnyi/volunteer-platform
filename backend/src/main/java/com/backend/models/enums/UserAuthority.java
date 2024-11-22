package com.backend.models.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing the different authorities (roles) that a user can have in the system.
 * Each authority represents a level of access or permission a user has within the application.
 * The authority also has a weight associated with it, indicating the relative importance or
 * priority of the authority (higher weights indicate higher roles).
 *
 * The authorities implement the `GrantedAuthority` interface, which is used by Spring Security
 * to manage and control access to resources based on the user's roles.
 */
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
