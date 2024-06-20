package dev.university.degree.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Appointment appointment;
    @ManyToOne
    private Diagnosis diagnosis;
    private String prescription;
    @Nullable
    private String comment;
}
