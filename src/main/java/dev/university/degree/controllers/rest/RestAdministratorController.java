package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Appointment;
import dev.university.degree.repositories.AppointmentRepository;
import dev.university.degree.util.AppointmentTimeHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/administrator")
public class RestAdministratorController {
    private final AppointmentRepository appointmentRepository;
    public RestAdministratorController(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/free-time")
    public List<String> getFreeTime(String date, Long vetId){
        LocalDate dateOfAppointment = LocalDate.parse(date);
        List<String> freeTimesList = AppointmentTimeHelper.getTimesForDay(dateOfAppointment);
        List<Appointment> appointments = appointmentRepository.findAllByAppointmentDateAndVetId(dateOfAppointment, vetId);

        appointments.forEach(appointment -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(appointment.getAppointmentTime().getHour());
            stringBuilder.append(":");
            stringBuilder.append(appointment.getAppointmentTime().getMinute());
            if(appointment.getAppointmentTime().getMinute() == 0){
                stringBuilder.append("0");
            }
            String string = stringBuilder.toString();
            freeTimesList.remove(string);
        });
        return freeTimesList;
    }

}
