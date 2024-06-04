package dev.university.degree.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String kind;
    private String breed;
    private LocalDate birthday;
    @ManyToOne
    private Client client;
    @ManyToOne
    @Nullable
    private Employee vet;
}
