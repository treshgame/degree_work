package dev.university.degree.util;

import lombok.Getter;

public enum Unit {
    PIECE("Поштучно"),
    MILLILITERS("Миллилитры");

    @Getter
    private final String unitName;

    Unit(String unitName){
        this.unitName = unitName;
    }
}
