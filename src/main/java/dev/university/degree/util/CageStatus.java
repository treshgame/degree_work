package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum CageStatus {
    FREE("Свободная"),
    BUSY("Занята");

    private String status;

    CageStatus(String status){
        this.status = status;
    }
}
