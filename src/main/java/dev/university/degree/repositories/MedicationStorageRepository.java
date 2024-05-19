package dev.university.degree.repositories;

import dev.university.degree.entities.MedicationStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationStorageRepository extends JpaRepository<MedicationStorage, Long> {

    @Query("SELECT m FROM MedicationStorage m WHERE m.medication.id = :medicationId AND m.supplyPrice = :price")
    public Optional<MedicationStorage> findByMedicationIdAndPrice(
            @Param("medicationId") long medicationId,
            @Param("price") double price
    );
}
