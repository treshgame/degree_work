package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Inpatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Animal animal;
    private LocalDate dateOfArrival;
    private LocalDate dateOfLeave;
}
