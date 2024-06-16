package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Medication;
import dev.university.degree.entities.Procedure;
import dev.university.degree.entities.Supplier;
import dev.university.degree.repositories.MedicationRepository;
import dev.university.degree.repositories.MedicationStorageRepository;
import dev.university.degree.repositories.ProcedureRepository;
import dev.university.degree.repositories.SupplierRepository;
import dev.university.degree.util.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class RestOwnerController {
    private final ProcedureRepository procedureRepository;
    private final MedicationRepository medicationRepository;
    private final SupplierRepository supplierRepository;

    public RestOwnerController(
            ProcedureRepository procedureRepository,
            MedicationRepository medicationRepository,
            SupplierRepository supplierRepository
    ){
        this.procedureRepository = procedureRepository;
        this.medicationRepository = medicationRepository;
        this.supplierRepository = supplierRepository;
    }

    @PostMapping("/add-procedure")
    public ResponseEntity<Object> addProcedure(
            @RequestParam String name,
            @RequestParam double price
    ){
        if(name.isEmpty() || name.trim().length() < 2){
            return ResponseEntity.badRequest().body("Слишком короткое название процедуры");
        }

        if(price < 0.01){
            return ResponseEntity.badRequest().body("Цена должны быть больше 0");
        }

        Procedure newProcedure = new Procedure();
        newProcedure.setName(name.trim());
        newProcedure.setPrice(price);
        newProcedure = procedureRepository.save(newProcedure);
        return ResponseEntity.ok(newProcedure);
    }

    @PutMapping("/update-procedure")
    public ResponseEntity<Object> updateProcedure(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam double price
    ){
        if(name.isEmpty() || name.trim().length() < 2){
            return ResponseEntity.badRequest().body("Слишком короткое название процедуры");
        }

        if(price < 0.01){
            return ResponseEntity.badRequest().body("Цена должны быть больше 0");
        }

        if(procedureRepository.findById(id).isEmpty()){
            return ResponseEntity.badRequest().body("Нет процедуры с таким id");
        }

        Procedure procedure = new Procedure(id, name, price);
        procedure = procedureRepository.save(procedure);
        return ResponseEntity.ok(procedure);
    }

    @PostMapping("/add-medication")
    public ResponseEntity<Object> addMedication(@RequestParam String name, @RequestParam Unit unit) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Слишком короткое название медикамента");
        }

        Medication newMedication = new Medication();
        newMedication.setName(name.trim());
        newMedication.setUnit(unit);
        newMedication = medicationRepository.save(newMedication);
        return ResponseEntity.ok(newMedication);
    }

    @PutMapping("/update-medication")
    public ResponseEntity<Object> updateMedication(@RequestParam long id, @RequestParam String name, @RequestParam Unit unit) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Слишком короткое название медикамента");
        }

        if (medicationRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Нет медикамента с таким id");
        }

        Medication medication = new Medication(id, name, unit);
        medication = medicationRepository.save(medication);
        return ResponseEntity.ok(medication);
    }

    @PostMapping("/add-supplier")
    public ResponseEntity<Object> addSupplier(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Слишком короткое название поставщика");
        }

        Supplier newSupplier = new Supplier();
        newSupplier.setName(name.trim());
        newSupplier.setPhoneNumber(phoneNumber);
        newSupplier.setEmail(email);
        newSupplier = supplierRepository.save(newSupplier);
        return ResponseEntity.ok(newSupplier);
    }

    @PutMapping("/update-supplier")
    public ResponseEntity<Object> updateSupplier(@RequestParam long id, @RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Слишком короткое название поставщика");
        }

        if (supplierRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Нет поставщика с таким id");
        }

        Supplier supplier = new Supplier(id, name, phoneNumber, email);
        supplier = supplierRepository.save(supplier);
        return ResponseEntity.ok(supplier);
    }

}
