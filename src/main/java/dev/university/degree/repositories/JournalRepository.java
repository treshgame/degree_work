package dev.university.degree.repositories;

import dev.university.degree.entities.Appointment;
import dev.university.degree.entities.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    Journal findByAppointment(Appointment appointment);
}
