package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum CageSize {
    LARGE("Большая"),
    MIDDLE("Средняя"),
    SMALL("Маленькая");

    private String size;

    CageSize(String size){
        this.size = size;
    }
}
