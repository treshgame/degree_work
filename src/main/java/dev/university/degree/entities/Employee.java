package dev.university.degree.entities;

import dev.university.degree.util.EmployeeStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;
    private String middleName;
    private LocalDateTime jobStart;
    private LocalDateTime timeQuited;
    private EmployeeStatus status;
}
