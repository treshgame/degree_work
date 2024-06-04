package dev.university.degree.entities;

import dev.university.degree.util.EmployeeStatus;
import dev.university.degree.util.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;
    private String middleName;
    private Job job;
    private LocalDate jobStart;
    private LocalDate timeQuited;
    private EmployeeStatus status;
}
