package dev.university.degree.controllers;

import dev.university.degree.entities.Animal;
import dev.university.degree.entities.Appointment;
import dev.university.degree.entities.Client;
import dev.university.degree.entities.Employee;
import dev.university.degree.repositories.AnimalRepository;
import dev.university.degree.repositories.AppointmentRepository;
import dev.university.degree.repositories.ClientRepository;
import dev.university.degree.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/administrator")
@Slf4j
public class AdministratorController {
    ClientRepository clientRepository;
    AnimalRepository animalRepository;
    EmployeeRepository employeeRepository;
    AppointmentRepository appointmentRepository;
    @Autowired
    public AdministratorController(
        ClientRepository clientRepository,
        AnimalRepository animalRepository,
        EmployeeRepository employeeRepository,
        AppointmentRepository appointmentRepository
    ){
        this.clientRepository = clientRepository;
        this.animalRepository = animalRepository;
        this.employeeRepository = employeeRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    public String index(){
        return "administrator/administrator_index";
    }

    @GetMapping("/add_client")
    public String addClientPage(Model model){
        model.addAttribute("client", new Client());
        return "administrator/add_client";
    }

    @GetMapping("/add_animal")
    public String addAnimalPage(Model model){
        model.addAttribute("animal", new Animal());
        model.addAttribute("clients", clientRepository.findAll());
        return "administrator/add_animal";
    }

    @PostMapping("/add_client")
    public String addClient(@ModelAttribute("client") Client client){
        System.out.println(client);
        clientRepository.save(client);
        return "redirect:/administrator/";
    }

    @PostMapping("/add_animal")
    public String addAnimal(@ModelAttribute("animal") Animal animal){
        System.out.println(animal);
        animalRepository.save(animal);
        return "redirect:/administrator/";
    }

    @GetMapping("/animals")
    public String animalsList(Model model){
        model.addAttribute("animals", animalRepository.findAll());
        return "administrator/animals";
    }

    @GetMapping("/clients")
    public String clientsList(Model model){
        model.addAttribute("clients", clientRepository.findAll());
        return "administrator/clients";
    }

    @GetMapping("/new-appointment")
    public String newAppointmentPage(Model model){
        model.addAttribute("animals", animalRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "administrator/new_appointment";
    }

    @PostMapping("/new-appointment")
    public String newAppointment(
            @RequestParam Long animalId,
            @RequestParam Long vetId,
            @RequestParam("appointment_date") String appointmentDate,
            @RequestParam("appointment_time") String freeTime

    ){
        Appointment appointment = new Appointment();
        String[] hourAndMinutes = freeTime.split(":");
        LocalDate localDateAppointment = LocalDate.parse(appointmentDate);
        LocalTime localFreeTime =LocalTime.of(
                Integer.parseInt(hourAndMinutes[0]),
                Integer.parseInt(hourAndMinutes[1]),
                0);

        if(appointmentRepository.findByDateTimeAndVet(vetId, localDateAppointment, localFreeTime).isPresent()){

            return "redirect:/administrator/new-appointment?error=time-taken";
        }
        Employee employee = employeeRepository.findById(vetId).orElse(null);
        if(employee == null){
            return "redirect:/administrator/new-appointment?error=no-employee";
        }
        Animal animal = animalRepository.findById(animalId).orElse(null);
        if(animal == null){
            return "redirect:/administrator/new-appointment?error=no-animal";
        }
        appointment.setVet(employee);
        appointment.setAnimal(animal);
        appointment.setAppointmentDate(localDateAppointment);
        appointment.setAppointmentTime(localFreeTime);
        log.info("Appointment: " + appointment);
        appointmentRepository.save(appointment);
        return "redirect:/administrator/new-appointment";
    }
}
