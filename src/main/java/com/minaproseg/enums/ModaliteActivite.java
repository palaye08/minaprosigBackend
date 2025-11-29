// ModaliteActivite.java
package com.minaproseg.enums;

public enum ModaliteActivite {
    PRESENTIEL("Présentiel"),
    EN_LIGNE("En ligne"),
    HYBRIDE("Hybride");

    private final String displayName;
    ModaliteActivite(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}