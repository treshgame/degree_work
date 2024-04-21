package dev.university.degree.services;

import dev.university.degree.entities.Vet;
import dev.university.degree.repositories.EmployeeRepository;
import dev.university.degree.repositories.VetRepository;
import dev.university.degree.util.CheckErrors;
import dev.university.degree.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VetService {
    @Autowired
    private VetRepository vetRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    public VetService(VetRepository vetRepository, EmployeeRepository employeeRepository) {
//        this.vetRepository = vetRepository;
//        this.employeeRepository = employeeRepository;
//    }

    public List<Vet> list() {
        return vetRepository.findAll();
    }

    public ServiceResponse<Vet> insert(Vet vet) {
        ServiceResponse<Vet> checkedEmployee = validateVet(vet);
        if (checkedEmployee != null) {
            return checkedEmployee;
        }

        Vet newVet = vetRepository.save(vet);
        return new ServiceResponse<>(201, "Ветеринар успешно добавлен", newVet);
    }

    public ServiceResponse<Vet> update(Vet vet) {
        if (vet.getId() == null) {
            return new ServiceResponse<>(400, "Не указан id ветеринара", null);
        }

        if (!vetRepository.existsById(vet.getId())) {
            return new ServiceResponse<>(400, "Ветеринара с таким id не существует", null);
        }

        ServiceResponse<Vet> checkedEmployee = validateVet(vet);
        if (checkedEmployee != null) {
            return checkedEmployee;
        }

        return new ServiceResponse<>(200, "Информация о ветеринаре успешно обновлена", null);
    }

    public ServiceResponse<Vet> delete(Long id) {
        if(!vetRepository.existsById(id)){
            return new ServiceResponse<>(400, "Ветеринара с таким Id не существует", null);
        }

        vetRepository.deleteById(id);

        if(vetRepository.existsById(id)){
            return new ServiceResponse<>(500, "При удалении произошла ошибка", null);
        }

        return new ServiceResponse<>(200, "Ветеринар успешно удален", null);
    }

    private ServiceResponse<Vet> validateVet(Vet vet) {
        String[] validateErrors = new String[4];
        if (!employeeRepository.existsById(vet.getEmployee_id().getId())) {
            validateErrors[0] = "Нет сотрудника с таким Id";
        }

        if (LocalDate.now().isBefore(vet.getStartOfWork())) {
            validateErrors[1] = "Дата начала работы позже сегодняшней";
        }

        if (CheckErrors.isThereAnError(validateErrors)) {
            return new ServiceResponse<>(400, "Ошибка валидации", null, validateErrors);
        }

        return null;
    }
}
