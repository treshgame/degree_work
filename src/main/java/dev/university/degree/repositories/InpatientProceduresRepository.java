package dev.university.degree.repositories;

import dev.university.degree.entities.InpatientProcedures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InpatientProceduresRepository extends JpaRepository<InpatientProcedures, Long> {
}
