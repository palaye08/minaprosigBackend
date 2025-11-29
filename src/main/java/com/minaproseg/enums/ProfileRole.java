package com.minaproseg.enums;

public enum ProfileRole {
    BENEFICIAIRE("Bénéficiaire"),
    COACH("Coach"),
    ADMIN("Administrateur");

    private final String displayName;

    ProfileRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}