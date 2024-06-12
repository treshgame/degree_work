package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentProcedures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Appointment appointment;
    @ManyToOne
    private Procedure procedure;
    @ManyToOne
    private MedicationStorage medicationStorage;
    private int medicationAmount;
}
