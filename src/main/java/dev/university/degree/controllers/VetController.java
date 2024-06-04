package dev.university.degree.controllers;

import dev.university.degree.entities.Appointment;
import dev.university.degree.repositories.AppointmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vet")
public class VetController {
    AppointmentRepository appointmentRepository;
    public VetController(
            AppointmentRepository appointmentRepository
    ){
        this.appointmentRepository = appointmentRepository;
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
    public String appointment(@PathVariable Long id){
        return "";
    }
}
