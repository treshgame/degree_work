package dev.university.degree.controllers;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.util.CageStatus;
import dev.university.degree.util.InpatientStatus;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inpatient")
public class InpatientController {
    CageRepository cageRepository;
    InpatientRepository inpatientRepository;
    AnimalRepository animalRepository;
    InpatientProceduresRepository inpatientProceduresRepository;
    ProcedureRepository procedureRepository;
    MedicationStorageRepository medicationStorageRepository;
    ReceiptRepository receiptRepository;
    public InpatientController(
            CageRepository cageRepository,
            InpatientRepository inpatientRepository,
            InpatientProceduresRepository inpatientProceduresRepository,
            AnimalRepository animalRepository,
            ProcedureRepository procedureRepository,
            MedicationStorageRepository medicationStorageRepository,
            ReceiptRepository receiptRepository
    ){
        this.cageRepository = cageRepository;
        this.inpatientRepository = inpatientRepository;
        this.inpatientProceduresRepository = inpatientProceduresRepository;
        this.animalRepository = animalRepository;
        this.procedureRepository = procedureRepository;
        this.medicationStorageRepository = medicationStorageRepository;
        this.receiptRepository = receiptRepository;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        List<Inpatient> inpatients = inpatientRepository.findByStatus(InpatientStatus.ILL);
        List<Cage> cages = cageRepository.findAll();
        model.addAttribute("inpatients", inpatients);
        model.addAttribute("freeCages", cages.stream().filter(cage -> cage.getCageStatus() == CageStatus.FREE).toList());
        return "inpatient/index";
    }

    @GetMapping("/add_procedure/{id}")
    public String inpatientProcedures(@PathVariable Long id, Model model){
        model.addAttribute("inpatientProcedure", new InpatientProcedures());
        model.addAttribute("inpatient", inpatientRepository.findById(id).orElse(null));
        model.addAttribute("inpatientId", id);
        List<Inpatient> inpatients = inpatientRepository.findAll();
        List<Procedure> procedures = procedureRepository.findAll();
        List<MedicationStorage> medicationStorages = medicationStorageRepository.findAll();

        model.addAttribute("inpatients", inpatients);
        model.addAttribute("procedures", procedures);
        model.addAttribute("medicationStorages", medicationStorages);

        List<InpatientProcedures> inpatientProcedures = inpatientProceduresRepository.findByInpatientId(id);
        model.addAttribute("inpatientProcedures", inpatientProcedures);

        return "/inpatient/add_procedure";
    }

    @PostMapping("/add-procedure")
    @Transactional
    public String addInpatientProcedure(@ModelAttribute InpatientProcedures inpatientProcedures, @RequestParam Long inpatientId){
        Optional<Inpatient> inpatient = inpatientRepository.findById(inpatientId);
        if(inpatient.isEmpty()){
            return "redirect:/inpatient";
        }

        inpatientProcedures.setInpatient(inpatient.get());
        inpatientProcedures.setProcedureTime(LocalDateTime.now());
        inpatientProceduresRepository.save(inpatientProcedures);

        MedicationStorage medicationStorage = inpatientProcedures.getMedicationStorage();
        medicationStorage.setAmount(medicationStorage.getAmount() - inpatientProcedures.getAmount());
        medicationStorageRepository.save(medicationStorage);

        return "redirect:/inpatient";
    }

    @PostMapping("/discharge/{id}")
    public String discharge(@PathVariable Long id){
        Inpatient inpatient = inpatientRepository.findById(id).orElse(null);
        if(inpatient == null){
            return "redirect:/inpatient";
        }

        inpatient.setStatus(InpatientStatus.DISCHARGED);
        LocalDate leaveDate = LocalDate.now();
        inpatient.setDateOfLeave(leaveDate);
        inpatientRepository.save(inpatient);

        Receipt receipt = new Receipt();
        List<InpatientProcedures> inpatientProcedures
                = inpatientProceduresRepository.findByInpatientId(inpatient.getId());
        receipt.setSum(0);
        for (InpatientProcedures inpatientProcedure : inpatientProcedures) {
            receipt.setSum(receipt.getSum() + inpatientProcedure.getProcedure().getPrice());
            receipt.setSum(
                receipt.getSum()
                        + (((inpatientProcedure.getMedicationStorage().getSupplyPrice() / 100 * 20)
                        + inpatientProcedure.getMedicationStorage().getSupplyPrice()) * inpatientProcedure.getAmount())
            );
        }
        long daysBetween = ChronoUnit.DAYS.between(inpatient.getDateOfArrival(), leaveDate);
        if(daysBetween == 0) daysBetween = 1;
        receipt.setSum(receipt.getSum() + (inpatient.getCage().getPricePerDay() * daysBetween));
        receiptRepository.save(receipt);

        Cage cage = inpatient.getCage();
        cage.setCageStatus(CageStatus.FREE);
        cageRepository.save(cage);
        return "redirect:/inpatient";
    }
}
