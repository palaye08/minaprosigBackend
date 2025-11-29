// CategorieDocument.java
package com.minaproseg.enums;

public enum CategorieDocument {
    ADMINISTRATIF("Administratif"),
    FINANCIER("Financier"),
    STRATEGIQUE("Stratégique"),
    AUTRE("Autre");

    private final String displayName;
    CategorieDocument(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}