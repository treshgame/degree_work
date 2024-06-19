package dev.university.degree.repositories;

import dev.university.degree.entities.AnimalKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
}
