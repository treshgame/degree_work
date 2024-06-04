package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    WAITING("Ожидает"),
    IN_PROCESS("В процессе"),
    CANCELLED("Отменен"),
    FINISHED("Завершен");
    
    private final String status;

    AppointmentStatus(String status){
        this.status = status;
    }
}
