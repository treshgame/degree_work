package dev.university.degree.entities;

import dev.university.degree.util.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "unit"})
    }
)
@AllArgsConstructor
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Unit unit;
    private int amount;
}
