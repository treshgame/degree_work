package dev.university.degree.controllers;

import dev.university.degree.entities.Animal;
import dev.university.degree.entities.Client;
import dev.university.degree.repositories.AnimalRepository;
import dev.university.degree.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
    ClientRepository clientRepository;
    AnimalRepository animalRepository;
    @Autowired
    public AdministratorController(
        ClientRepository clientRepository,
        AnimalRepository animalRepository
    ){
        this.clientRepository = clientRepository;
        this.animalRepository = animalRepository;
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
}
