package com.phobod.leaflet.model;

import lombok.Getter;

public enum PinColor {
    BLUE("pin-blue.svg"),
    CYAN("pin-cyan.svg"),
    GREEN("pin-green.svg"),
    ORANGE("pin-orange.svg"),
    PURPLE("pin-purple.svg");

    @Getter
    private String pinName;

    PinColor(String pinName) {
        this.pinName = pinName;
    }
}
