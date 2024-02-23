package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private Employee employee;
    private String type;
    private String comment;
}
