// CategorieObjectif.java
package com.minaproseg.enums;

public enum CategorieObjectif {
    FINANCIER("Financier"),
    COMMERCIAL("Commercial"),
    ORGANISATIONNEL("Organisationnel"),
    STRATEGIQUE("Stratégique"),
    PERSONNEL("Personnel");

    private final String displayName;
    CategorieObjectif(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}