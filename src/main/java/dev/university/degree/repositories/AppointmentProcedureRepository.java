package dev.university.degree.repositories;

import dev.university.degree.entities.AppointmentProcedures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentProcedureRepository extends JpaRepository<AppointmentProcedures, Long> {
}
