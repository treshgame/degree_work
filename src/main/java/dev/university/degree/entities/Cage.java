package dev.university.degree.entities;

import dev.university.degree.util.CageSize;
import dev.university.degree.util.CageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private CageSize cageSize;
    private double pricePerDay;
    private CageStatus cageStatus;
    private LocalDateTime lastCleaningTime;

    public String getFormattedDateTime(){
        return lastCleaningTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                + " "
                + lastCleaningTime.format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8);
    }

}
