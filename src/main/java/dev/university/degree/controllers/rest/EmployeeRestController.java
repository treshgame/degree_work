package dev.university.degree.controllers.rest;

import dev.university.degree.entities.Employee;
import dev.university.degree.services.EmployeeService;
import dev.university.degree.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.list();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        ServiceResponse<Employee> response = employeeService.insert(employee);
        if (response.getCode() == 201) {
            return new ResponseEntity<>(response.getValue(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
        ServiceResponse<Employee> response = employeeService.update(employee);
        if (response.getCode() == 200) {
            return new ResponseEntity<>(response.getValue(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        ServiceResponse<Employee> response = employeeService.delete(id);
        if (response.getCode() == 200) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.getValidateErrors(), HttpStatus.BAD_REQUEST);
        }
    }
}
