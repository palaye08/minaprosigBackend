// StatutObjectif.java
package com.minaproseg.enums;

public enum StatutObjectif {
    EN_COURS("En cours"),
    ATTEINT("Atteint"),
    NON_ATTEINT("Non atteint"),
    ABANDONNE("Abandonné");

    private final String displayName;
    StatutObjectif(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}