package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Employee employee_id;
    private String spec;
    private String secondSpec;
    private LocalDateTime startOfWork;
}
