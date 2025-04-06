package com.example.monitorsensors.models;

public enum MeasurementUnit {
    BAR("bar"),
    VOLTAGE("voltage"),
    CELSIUS("°С"),
    PERCENT("%");

    private final String displayName;

    MeasurementUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
