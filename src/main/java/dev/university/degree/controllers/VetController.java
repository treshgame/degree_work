package dev.university.degree.controllers;

import dev.university.degree.entities.Appointment;
import dev.university.degree.repositories.AppointmentRepository;
import dev.university.degree.repositories.MedicationStorageRepository;
import dev.university.degree.repositories.ProcedureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vet")
public class VetController {
    AppointmentRepository appointmentRepository;
    ProcedureRepository procedureRepository;
    MedicationStorageRepository medicationStorageRepository;

    public VetController(
            AppointmentRepository appointmentRepository,
            ProcedureRepository procedureRepository,
            MedicationStorageRepository medicationStorageRepository
    ){
        this.appointmentRepository = appointmentRepository;
        this.procedureRepository = procedureRepository;
        this.medicationStorageRepository = medicationStorageRepository;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        List<Appointment> appointmentList = appointmentRepository.findAll();
        List<Appointment> recentAppointmentList = appointmentRepository.findAll().stream().limit(10).toList();
        model.addAttribute("appointments", appointmentList);
        model.addAttribute("recentAppointments", recentAppointmentList);
        return "vet/appointments";
    }

    @GetMapping("/{id}")
    public String appointment(@PathVariable Long id, Model model){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isEmpty()){
            return "redirect:/vet?error=no-appointment";
        }
        model.addAttribute("appointment", appointment.get());
        model.addAttribute("procedures", procedureRepository.findAll());
        model.addAttribute("medications", medicationStorageRepository.findAll());
        return "vet/appointment";
    }
}
