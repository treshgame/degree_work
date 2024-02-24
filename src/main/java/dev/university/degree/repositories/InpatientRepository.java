package dev.university.degree.repositories;

import dev.university.degree.entities.Inpatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InpatientRepository extends JpaRepository<Inpatient, Long> {
}
