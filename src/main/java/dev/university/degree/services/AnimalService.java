package dev.university.degree.services;

import dev.university.degree.entities.Animal;
import dev.university.degree.repositories.AnimalRepository;
import dev.university.degree.repositories.OwnerRepository;
import dev.university.degree.repositories.VetRepository;
import dev.university.degree.util.CheckErrors;
import dev.university.degree.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    @Autowired
    public AnimalService(AnimalRepository animalRepository, VetRepository vetRepository,OwnerRepository ownerRepository){
        this.animalRepository = animalRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
    }
    public List<Animal> list(){
        return animalRepository.findAll();
    }

    public ServiceResponse<Animal> insert(Animal animal){
        ServiceResponse<Animal> checkedVetAndOwner = validateAnimal(animal);
        if(checkedVetAndOwner != null){
            return checkedVetAndOwner;
        }

        Animal newAnimal = animalRepository.save(animal);
        return new ServiceResponse<>(201, "Животное успешно добавлено", newAnimal);
    }

    public ServiceResponse<Animal> update(Animal animal){
        if(animal.getId() == null){
            return new ServiceResponse<>(400, "Не указан id животного", null);
        }
        if(!animalRepository.existsById(animal.getId())){
            return new ServiceResponse<>(400, "Животного с таким id не существует", null);
        }
        ServiceResponse<Animal> checkedVetAndOwner = validateAnimal(animal);
        if(checkedVetAndOwner != null){
            return checkedVetAndOwner;
        }

        return new ServiceResponse<>(200, "Информация о животном успешно обновлена", null);
    }

    public ServiceResponse<Animal> delete(Long id){
        if(!animalRepository.existsById(id)){
            return new ServiceResponse<>(400, "Животного с таким id не существует", null);
        }

        animalRepository.deleteById(id);

        if(animalRepository.existsById(id)){
            return new ServiceResponse<>(500, "Во время удаления произошла ошибка", null);
        }

        return new ServiceResponse<>(200,"Жиотное удалено", null);
    }

    private ServiceResponse<Animal> validateAnimal(Animal animal){
        String[] validateErrors = new String[6];
        if(animal.getName().length() > 255){
            validateErrors[0] = "Слишком длинное имя животного";
        }

        if(animal.getKind().length() > 255){
            validateErrors[1] = "Слишком длинное название вида животного";
        }

        if(animal.getBreed().length() > 255){
            validateErrors[2] = "Слишком длинное название породы животного";
        }

        if(!vetRepository.existsById(animal.getVet().getId())){
            validateErrors[3] = "Ветеринара с таким Id не существует";
        }

        if(!ownerRepository.existsById(animal.getOwner().getId())){
            validateErrors[4] = "Владельца с таким Id не существует";
        }

        if(LocalDate.now().isBefore(animal.getBirthday())){
            validateErrors[5] = "Дата рождения позже сегодняшней";
        }

        if(CheckErrors.isThereAnError(validateErrors)){
            return new ServiceResponse<>(400, "Ошибка валидации", null, validateErrors);
        }

        return null;
    }

}
