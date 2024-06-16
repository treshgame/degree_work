package dev.university.degree.repositories;

import dev.university.degree.entities.Inpatient;
import dev.university.degree.util.InpatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InpatientRepository extends JpaRepository<Inpatient, Long> {
    Optional<Inpatient> findByAnimalId(Long animalId);
    Optional<Inpatient> findByAnimalIdAndStatus(Long animalId, InpatientStatus status);
    List<Inpatient> findByStatus(InpatientStatus status);
}
