// Genre.java
package com.minaproseg.enums;

public enum Genre {
    MASCULIN("Masculin"),
    FEMININ("Féminin");

    private final String displayName;
    Genre(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}