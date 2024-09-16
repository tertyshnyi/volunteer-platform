package com.backend.models.enums;

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
