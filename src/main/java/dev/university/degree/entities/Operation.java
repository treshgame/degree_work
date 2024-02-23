package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private Vet operator;
    @ManyToOne
    private Vet assistant;
}
