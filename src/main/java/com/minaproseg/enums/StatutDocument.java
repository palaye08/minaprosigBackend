// StatutDocument.java
package com.minaproseg.enums;

public enum StatutDocument {
    TELECHARGE("Téléchargé"),
    NON_TELECHARGE("Non téléchargé"),
    COMPLETE("Complété");

    private final String displayName;
    StatutDocument(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}