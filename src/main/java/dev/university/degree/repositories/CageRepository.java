package dev.university.degree.repositories;

import dev.university.degree.entities.Cage;
import dev.university.degree.util.CageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CageRepository extends JpaRepository<Cage, Long> {
    List<Cage> findByCageStatus(CageStatus status);
}
