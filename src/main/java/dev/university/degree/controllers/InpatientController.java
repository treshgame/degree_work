package dev.university.degree.controllers;

import dev.university.degree.entities.Animal;
import dev.university.degree.entities.Cage;
import dev.university.degree.entities.Inpatient;
import dev.university.degree.entities.InpatientProcedures;
import dev.university.degree.repositories.AnimalRepository;
import dev.university.degree.repositories.CageRepository;
import dev.university.degree.repositories.InpatientProceduresRepository;
import dev.university.degree.repositories.InpatientRepository;
import dev.university.degree.util.CageStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/inpatient")
public class InpatientController {
    CageRepository cageRepository;
    InpatientRepository inpatientRepository;
    AnimalRepository animalRepository;

    InpatientProceduresRepository inpatientProceduresRepository;
    public InpatientController(
            CageRepository cageRepository,
            InpatientRepository inpatientRepository,
            InpatientProceduresRepository inpatientProceduresRepository,
            AnimalRepository animalRepository
    ){
        this.cageRepository = cageRepository;
        this.inpatientRepository = inpatientRepository;
        this.inpatientProceduresRepository = inpatientProceduresRepository;
        this.animalRepository = animalRepository;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        List<Inpatient> inpatients = inpatientRepository.findAll();
        List<Cage> cages = cageRepository.findAll();
        model.addAttribute("inpatients", inpatients);
        model.addAttribute("freeCages", cages.stream().filter(cage -> cage.getCageStatus() == CageStatus.FREE).toList());
        return "inpatient/index";
    }

    @GetMapping("/to_inpatient")
    public String index(){
        Animal animal = animalRepository.findById(1L).orElse(null);
        Inpatient inpatient = new Inpatient();
        inpatient.setAnimal(animal);
        inpatient.setCage(cageRepository.findById(1L).orElse(null));
        inpatient.setDateOfArrival(LocalDate.now());
        inpatientRepository.save(inpatient);
        return "redirect:/inpatient";
    }
}
