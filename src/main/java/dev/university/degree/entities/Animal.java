package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String kind;
    private String breed;
    private LocalDate birthday;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private Vet vet;
}
