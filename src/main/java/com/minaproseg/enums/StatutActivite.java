// StatutActivite.java
package com.minaproseg.enums;

public enum StatutActivite {
    INSCRIT("Inscrit"),
    CONFIRME("Confirmé"),
    EN_ATTENTE("En attente"),
    PRESENT("Présent"),
    ABSENT("Absent"),
    EXCUSE("Excusé");

    private final String displayName;
    StatutActivite(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}