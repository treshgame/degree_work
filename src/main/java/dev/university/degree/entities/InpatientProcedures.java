package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InpatientProcedures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Inpatient inpatient;
    @ManyToOne
    private Procedure procedure;
    @ManyToOne
    private MedicationStorage medicationStorage;
    private LocalDateTime procedureTime;
    private double amount;

    public String formattedDateTime(){
        return procedureTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + " "
                + procedureTime.format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8);
    }
}
