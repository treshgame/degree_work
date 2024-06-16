package dev.university.degree.controllers.rest;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.util.EmployeeStatus;
import dev.university.degree.util.Job;
import dev.university.degree.util.Unit;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

import static dev.university.degree.util.Job.VET;

@RestController
@RequestMapping("/owner")
public class RestOwnerController {
    private final ProcedureRepository procedureRepository;
    private final MedicationRepository medicationRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final EmployeeRepository employeeRepository;
    PasswordEncoder passwordEncoder;

    public RestOwnerController(
            ProcedureRepository procedureRepository,
            MedicationRepository medicationRepository,
            SupplierRepository supplierRepository,
            UserRepository userRepository,
            AuthorityRepository authorityRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder
    ){
        this.procedureRepository = procedureRepository;
        this.medicationRepository = medicationRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/add-employee")
    public ResponseEntity<Object> addEmployee(
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String middleName,
            @RequestParam Job job,
            @RequestParam String login,
            @RequestParam String password
    ) {
        if (firstName.isEmpty() || firstName.trim().length() < 2 || surname.isEmpty() || surname.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Имя и фамилия должны быть длиннее");
        }

        if (userRepository.existsByUsername(login.trim())) {
            return ResponseEntity.badRequest().body("Пользователь с таким логином уже существует");
        }

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(firstName.trim());
        newEmployee.setSurname(surname.trim());
        newEmployee.setMiddleName(middleName.trim());
        newEmployee.setJob(job);
        newEmployee.setJobStart(LocalDate.now());
        newEmployee.setStatus(EmployeeStatus.EMPLOYED);
        newEmployee = employeeRepository.save(newEmployee);

        User newUser = new User();
        newUser.setUsername(login.trim());
        newUser.setPassword(passwordEncoder.encode(password.trim()));
        newUser.setEnabled(true);
        newUser.setEmployee(newEmployee);
        userRepository.save(newUser);

        Authority authority = new Authority();
        authority.setAuthority(job.toString());
        authority.setUser(newUser);
        authorityRepository.save(authority);

        return ResponseEntity.ok(newEmployee);
    }

    @PutMapping("/update-employee")
    public ResponseEntity<Object> updateEmployee(
            @RequestParam long id,
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String middleName,
            @RequestParam Job job,
            @RequestParam EmployeeStatus status,
            @Nullable @RequestParam LocalDate timeQuited
    ) {
        if (firstName.isEmpty() || firstName.trim().length() < 2 || surname.isEmpty() || surname.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Имя и фамилия должны быть длиннее");
        }

        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.badRequest().body("Нет сотрудника с таким id");
        }

        employee.setFirstName(firstName.trim());
        employee.setSurname(surname.trim());
        employee.setMiddleName(middleName.trim());
        employee.setJob(job);
        employee.setStatus(status);
        employee.setTimeQuited(status == EmployeeStatus.FIRED ? LocalDate.now() : null);

        employee = employeeRepository.save(employee);

        User user = userRepository.findByEmployee(employee);
        if (user != null) {
            String role = switch (employee.getJob()) {
                case VET -> "VET";
                case INPATIENT -> "INPATIENT";
                case ADMINISTRATOR -> "ADMINISTRATOR";
            };

            Authority authority = authorityRepository.findByUser(user);
            authority.setAuthority(role);
            authorityRepository.save(authority);
        }

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Object> checkUsername(@RequestParam String username) {
        boolean exists = userRepository.existsByUsername(username);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

}
