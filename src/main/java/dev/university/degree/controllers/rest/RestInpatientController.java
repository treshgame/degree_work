package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Cage;
import dev.university.degree.repositories.CageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/inpatient")
public class RestInpatientController {
    CageRepository cageRepository;
    public RestInpatientController(
            CageRepository cageRepository
    ){
        this.cageRepository = cageRepository;
    }
    @PostMapping("/cageClean")
    public ResponseEntity<String> cleanCage(@RequestParam Long cageId){
        LocalDateTime localDateTime = LocalDateTime.now();
        Cage cage = cageRepository.findById(cageId).orElse(null);
        if(cage == null){
            return ResponseEntity.badRequest().body("Нет клетки с таким id");
        }
        cage.setLastCleaningTime(localDateTime);
        return ResponseEntity.ok(cage.getFormattedDateTime());
    }

}
