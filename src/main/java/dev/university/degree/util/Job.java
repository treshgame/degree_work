package dev.university.degree.util;

public enum Job {
    VET("Ветеринар"),
    ADMINISTRATOR("Администратор"),
    INPATIENT("Работник стационара");

    private String name;

    Job(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
