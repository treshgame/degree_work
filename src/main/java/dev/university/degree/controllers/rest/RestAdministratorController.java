package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Animal;
import dev.university.degree.entities.Appointment;
import dev.university.degree.entities.Client;
import dev.university.degree.repositories.AnimalRepository;
import dev.university.degree.repositories.AppointmentRepository;
import dev.university.degree.repositories.ClientRepository;
import dev.university.degree.util.AppointmentTimeHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/administrator")
public class RestAdministratorController {
    private final AppointmentRepository appointmentRepository;
    private final AnimalRepository animalRepository;
    private final ClientRepository clientRepository;
    public RestAdministratorController(
            AppointmentRepository appointmentRepository,
            AnimalRepository animalRepository,
            ClientRepository clientRepository
    ){
        this.appointmentRepository = appointmentRepository;
        this.animalRepository = animalRepository;
        this.clientRepository = clientRepository;
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

    @PostMapping("/add-animal")
    public ResponseEntity<Object> addAnimal(
            @RequestParam String name,
            @RequestParam String kind,
            @RequestParam String breed,
            @RequestParam LocalDate birthday,
            @RequestParam long clientId
    ) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Кличка должна быть длиннее");
        }

        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            return ResponseEntity.badRequest().body("Нет клиента с таким id");
        }

        Animal newAnimal = new Animal();
        newAnimal.setName(name.trim());
        newAnimal.setKind(kind.trim());
        newAnimal.setBreed(breed.trim());
        newAnimal.setBirthday(birthday);
        newAnimal.setClient(client);
        newAnimal = animalRepository.save(newAnimal);

        return ResponseEntity.ok(newAnimal);
    }

    @PutMapping("/update-animal")
    public ResponseEntity<Object> updateAnimal(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String kind,
            @RequestParam String breed,
            @RequestParam LocalDate birthday,
            @RequestParam long clientId
    ) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Кличка должна быть длиннее");
        }

        Animal animal = animalRepository.findById(id).orElse(null);
        if (animal == null) {
            return ResponseEntity.badRequest().body("Нет животного с таким id");
        }

        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            return ResponseEntity.badRequest().body("Нет клиента с таким id");
        }

        animal.setName(name.trim());
        animal.setKind(kind.trim());
        animal.setBreed(breed.trim());
        animal.setBirthday(birthday);
        animal.setClient(client);

        animal = animalRepository.save(animal);

        return ResponseEntity.ok(animal);
    }

    @PostMapping("/add-client")
    public ResponseEntity<Object> addClient(
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String middleName,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String email
    ) {
        if (firstName.isEmpty() || firstName.trim().length() < 2 || surname.isEmpty() || surname.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Имя и фамилия должны быть длиннее");
        }

        Client newClient = new Client();
        newClient.setFirstName(firstName.trim());
        newClient.setSurname(surname.trim());
        newClient.setMiddleName(middleName.trim());
        newClient.setPhoneNumber(phoneNumber.trim());
        newClient.setEmail(email != null ? email.trim() : null);
        newClient = clientRepository.save(newClient);

        return ResponseEntity.ok(newClient);
    }

    @PutMapping("/update-client")
    public ResponseEntity<Object> updateClient(
            @RequestParam long id,
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String middleName,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String email
    ) {
        if (firstName.isEmpty() || firstName.trim().length() < 2 || surname.isEmpty() || surname.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Имя и фамилия должны быть длиннее");
        }

        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return ResponseEntity.badRequest().body("Нет клиента с таким id");
        }

        client.setFirstName(firstName.trim());
        client.setSurname(surname.trim());
        client.setMiddleName(middleName.trim());
        client.setPhoneNumber(phoneNumber.trim());
        client.setEmail(email != null ? email.trim() : null);

        client = clientRepository.save(client);

        return ResponseEntity.ok(client);
    }

}
