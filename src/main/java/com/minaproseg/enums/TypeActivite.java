package com.minaproseg.enums;

// TypeActivite.java
public enum TypeActivite {
    FORMATION("Formation"),
    ATELIER("Atelier"),
    WEBINAIRE("Webinaire"),
    EVENEMENT("Événement");

    private final String displayName;
    TypeActivite(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}