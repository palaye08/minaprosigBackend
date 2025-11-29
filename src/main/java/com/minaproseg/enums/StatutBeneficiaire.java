package com.minaproseg.enums;

public enum StatutBeneficiaire {
    ACTIF("Actif"),
    EN_PAUSE("En pause"),
    SORTI("Sorti"),
    DIPLOME("Diplômé");

    private final String displayName;

    StatutBeneficiaire(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}