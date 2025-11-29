package com.minaproseg.enums;

public enum Zone {
    URBAINE("Urbaine"),
    RURALE("Rurale");

    private final String displayName;
    Zone(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}