package dev.university.degree.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
    private String username;
}