package dev.university.degree.entities;

import dev.university.degree.util.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private Employee vet;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;

    public Appointment(){
        status = AppointmentStatus.WAITING;
    }

    public String getFormattedTime(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appointmentTime.getHour());
        stringBuilder.append(":");
        stringBuilder.append(appointmentTime.getMinute());
        if(appointmentTime.getMinute() == 0){
            stringBuilder.append(0);
        }
        return stringBuilder.toString();
    }
}
