package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Animal;
import dev.university.degree.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnimalRestController {
//    @Autowired
//    private AnimalRepository animalRepository;
//    @Autowired
//    public AnimalRestController(AnimalRepository animalRepository){
//        this.animalRepository = animalRepository;
//    }
//    @GetMapping("/animals/list")
//    public ResponseEntity<List<Animal>> list(){
//        return ResponseEntity.ok().body(animalRepository.findAll());
//    }
//
//    @GetMapping("/animals/{id}")
//    public ResponseEntity<Animal> getById(@PathVariable Long id){
//        Optional<Animal> oAnimal = animalRepository.findById(id);
//        if(oAnimal.isPresent()){
//            return ResponseEntity.ok().body(oAnimal.get());
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/animals/add")
//    public ResponseEntity<Animal> add(@RequestBody Animal animal){
//        Animal newAnimal = animalRepository.save(animal);
//        return ResponseEntity.ok().body(newAnimal);
//    }
//
//    @PutMapping("/animals/add")
//    public ResponseEntity<Object> update(@RequestBody Animal animal){
//        if(animal.getId() != null && animal.getId() > 0){
//            animalRepository.save(animal);
//            return ResponseEntity.noContent().build();
//        }else{
//            return ResponseEntity.badRequest().body("Bad id or it's disappearing");
//        }
//    }
//
//    @DeleteMapping("/animals/delete/{id}")
//    public ResponseEntity<Object> delete(@PathVariable Long id){
//        animalRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
