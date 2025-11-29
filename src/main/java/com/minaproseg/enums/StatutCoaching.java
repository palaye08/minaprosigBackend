// StatutCoaching.java
package com.minaproseg.enums;

public enum StatutCoaching {
    PLANIFIE("Planifié"),
    COMPLETE("Complété"),
    ANNULE("Annulé");

    private final String displayName;
    StatutCoaching(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}