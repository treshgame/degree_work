package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum InpatientStatus {
    ILL("В стационаре"),
    DISCHARGED("Выписан");

    private final String status;
    InpatientStatus(String status){
        this.status = status;
    }
}
