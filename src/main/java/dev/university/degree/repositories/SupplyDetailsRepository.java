package dev.university.degree.repositories;

import dev.university.degree.entities.SupplyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyDetailsRepository extends JpaRepository<SupplyDetails, Long> {
}
