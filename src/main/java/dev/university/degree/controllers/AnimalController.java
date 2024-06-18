package dev.university.degree.controllers;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.util.Job;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {
    AnimalRepository animalRepository;
    JournalRepository journalRepository;
    AppointmentRepository appointmentRepository;
    AuthorityRepository authorityRepository;
    UserRepository userRepository;

    public AnimalController(
            AnimalRepository animalRepository,
            JournalRepository journalRepository,
            AppointmentRepository appointmentRepository,
            AuthorityRepository authorityRepository,
            UserRepository userRepository
    ){
        this.animalRepository = animalRepository;
        this.journalRepository = journalRepository;
        this.appointmentRepository = appointmentRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String getAnimal(@PathVariable Long id, Model model, Principal principal){
        Animal animal = animalRepository.findById(id).orElse(null);
        List<Appointment> appointments = appointmentRepository.findAllByAnimal(animal);
        List<Journal> journals = new ArrayList<>();
        for (Appointment appointment : appointments) {
            journals.add(journalRepository.findByAppointment(appointment));
        }
        String userRole = "OWNER";

        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null){
            Authority authority = authorityRepository.findByUser(user);
            if(authority.getAuthority().equals("VET")){
                userRole = "VET";
            }else if(authority.getAuthority().equals("ADMINISTRATOR")){
                userRole = "ADMINISTRATOR";
            }else if(authority.getAuthority().equals("INPATIENT")){
                userRole = "INPATIENT";
            }
        }

        model.addAttribute("journals", journals);
        model.addAttribute("animal", animal);
        model.addAttribute("userRole", userRole);
        return "animal";
    }
}
