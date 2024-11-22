package com.backend.models.enums;

/**
 * Enum representing the different roles an organization can have in the system.
 *
 * This enum categorizes organizations based on their role, such as suppliers, charities, and delivery companies.
 * Each role has a display name that can be used to present a user-friendly version of the role in the application
 * (e.g., in user interfaces or reports).
 */
public enum OrganizationRole {
    SUPPLIER_ORGANIZATION("Supplier Organization"),
    CHARITY_ORGANIZATION("Charity Organization"),
    DELIVERY_ORGANIZATION("Delivery Organization");

    private final String displayName;

    OrganizationRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
