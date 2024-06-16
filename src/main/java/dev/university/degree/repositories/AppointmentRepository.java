package dev.university.degree.repositories;

import dev.university.degree.entities.Animal;
import dev.university.degree.entities.Appointment;
import dev.university.degree.util.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
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

    List<Appointment> findAllByStatus(AppointmentStatus status);

    List<Appointment> findAllByAnimal(Animal animal);
}
