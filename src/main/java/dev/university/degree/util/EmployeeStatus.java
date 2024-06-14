package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum EmployeeStatus {
    EMPLOYED("Работает"),
    FIRED("Уволен"),
    VACATION("В отпуске");

    private final String status;
    EmployeeStatus(String status){
        this.status = status;
    }
}
