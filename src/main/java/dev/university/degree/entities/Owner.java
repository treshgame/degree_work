package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surname;
    private String middleName;
    private String phoneNumber;
    private String email;
}
