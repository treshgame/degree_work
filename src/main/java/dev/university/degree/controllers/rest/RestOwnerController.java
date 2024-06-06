package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Procedure;
import dev.university.degree.repositories.ProcedureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class RestOwnerController {
    private final ProcedureRepository procedureRepository;
    public RestOwnerController(
            ProcedureRepository procedureRepository
    ){
        this.procedureRepository = procedureRepository;
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
}
