package dev.university.degree.repositories;

import dev.university.degree.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByAppointmentDateAndVetId(LocalDate time, Long vetId);
    @Query("SELECT a FROM Appointment a " +
            "WHERE a.vet.id = :vetId " +
            "AND a.appointmentDate = :appointmentDate " +
            "AND a.appointmentTime = :appointmentTime"
    )
    Optional<Appointment> findByDateTimeAndVet(
            @Param("vetId") Long vetId,
            @Param("appointmentDate") LocalDate localDate,
            @Param("appointmentTime") LocalTime localTime
    );

}
