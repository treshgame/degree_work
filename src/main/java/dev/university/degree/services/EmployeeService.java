package dev.university.degree.services;

import dev.university.degree.entities.Employee;
import dev.university.degree.repositories.EmployeeRepository;
import dev.university.degree.util.CheckErrors;
import dev.university.degree.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> list(){
        return employeeRepository.findAll();
    }

    public ServiceResponse<Employee> insert(Employee employee){
        ServiceResponse<Employee> validateResponse = validateEmployee(employee);
        if(validateResponse != null){
            return validateResponse;
        }
        Employee newEmployee = employeeRepository.save(employee);
        return new ServiceResponse<>(201, "Новый работник успешно создан", newEmployee);
    }

    public ServiceResponse<Employee> update(Employee employee){
        if(!employeeRepository.existsById(employee.getId())){
            return new ServiceResponse<>(400, "Работника с таким id не существует", null);
        }

        return new ServiceResponse<>(200, "Информация о работнике обновлена", employee);
    }

    public ServiceResponse<Employee> delete(Long id){
        if(!employeeRepository.existsById(id)){
            return new ServiceResponse<>(400, "Работника с таким id не существует", null);
        }

        employeeRepository.deleteById(id);

        if(employeeRepository.existsById(id)){
            return new ServiceResponse<>(500, "Во время удаления работника произошла ошибка", null);
        }

        return new ServiceResponse<>(200, "Работник удален", null);
    }

    public ServiceResponse<Employee> validateEmployee(Employee employee){
        String[] errors = new String[3];
        if(employee.getFirstName().length() > 255){
            errors[0] = "Имя слишком длинное";
        }

        if(employee.getMiddleName().length() > 255){
            errors[1] = "Отчество слишком длинное";
        }

        if(employee.getSurname().length() > 255){
            errors[2] = "Фамилия слишком длинная";
        }

        if(CheckErrors.isThereAnError(errors)){
            return new ServiceResponse<>(400, "Ошибка валидации", null, errors);
        }

        return null;
    }

}
