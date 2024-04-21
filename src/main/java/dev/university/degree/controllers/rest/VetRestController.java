package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Vet;
import dev.university.degree.services.VetService;
import dev.university.degree.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetRestController {

    private final VetService vetService;

    @Autowired
    public VetRestController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Vet>> getAllVets() {
        List<Vet> vets = vetService.list();
        return new ResponseEntity<>(vets, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addVet(@RequestBody Vet vet) {
        ServiceResponse<Vet> response = vetService.insert(vet);
        if (response.getCode() == 201) {
            return new ResponseEntity<>(response.getValue(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateVet(@RequestBody Vet vet) {
        ServiceResponse<Vet> response = vetService.update(vet);
        if (response.getCode() == 200) {
            return new ResponseEntity<>(response.getValue(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteVet(@PathVariable Long id) {
        Vet vet = new Vet();
        vet.setId(id);
        ServiceResponse<Vet> response = vetService.delete(id);
        if (response.getCode() == 200) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }
}
