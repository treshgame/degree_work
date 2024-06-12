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
}
