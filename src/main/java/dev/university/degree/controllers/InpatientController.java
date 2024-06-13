package dev.university.degree.controllers;

import dev.university.degree.entities.Cage;
import dev.university.degree.entities.Inpatient;
import dev.university.degree.entities.InpatientProcedures;
import dev.university.degree.repositories.CageRepository;
import dev.university.degree.repositories.InpatientProceduresRepository;
import dev.university.degree.repositories.InpatientRepository;
import dev.university.degree.util.CageStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inpatient")
public class InpatientController {
    CageRepository cageRepository;
    InpatientRepository inpatientRepository;

    InpatientProceduresRepository inpatientProceduresRepository;
    public InpatientController(
            CageRepository cageRepository,
            InpatientRepository inpatientRepository,
            InpatientProceduresRepository inpatientProceduresRepository
    ){
        this.cageRepository = cageRepository;
        this.inpatientRepository = inpatientRepository;
        this.inpatientProceduresRepository = inpatientProceduresRepository;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        List<Inpatient> inpatients = inpatientRepository.findAll();
        List<Cage> cages = cageRepository.findAll();
        model.addAttribute("inpatients", inpatients);
        model.addAttribute("freeCages", cages.stream().filter(cage -> cage.getCageStatus() == CageStatus.FREE).toList());
        return "inpatient/index";
    }
}
