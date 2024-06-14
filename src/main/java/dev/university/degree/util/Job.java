package dev.university.degree.util;

import lombok.Getter;

@Getter
public enum Job {
    VET("Ветеринар"),
    ADMINISTRATOR("Администратор"),
    INPATIENT("Работник стационара");

    private final String name;

    Job(String name){
        this.name = name;
    }
}
