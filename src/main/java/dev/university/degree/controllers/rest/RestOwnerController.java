package dev.university.degree.controllers.rest;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.services.UserService;
import dev.university.degree.util.*;
import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static dev.university.degree.util.Job.*;

@RestController
@RequestMapping("/owner")
public class RestOwnerController {
    private final ProcedureRepository procedureRepository;
    private final MedicationRepository medicationRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final EmployeeRepository employeeRepository;
    private final CageRepository cageRepository;
    private final DiagnosisRepository diagnosisRepository;
    UserService userService;
    PasswordEncoder passwordEncoder;

    public RestOwnerController(
            ProcedureRepository procedureRepository,
            MedicationRepository medicationRepository,
            SupplierRepository supplierRepository,
            UserRepository userRepository,
            AuthorityRepository authorityRepository,
            EmployeeRepository employeeRepository,
            CageRepository cageRepository,
            DiagnosisRepository diagnosisRepository,
            PasswordEncoder passwordEncoder,
            UserService userService
    ){
        this.procedureRepository = procedureRepository;
        this.medicationRepository = medicationRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.employeeRepository = employeeRepository;
        this.cageRepository = cageRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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

        String role = switch (newEmployee.getJob()) {
            case VET -> "VET";
            case INPATIENT -> "INPATIENT";
            case ADMINISTRATOR -> "ADMINISTRATOR";
        };
       userService.registerNewUser(login, password, role, newEmployee);

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

    @PostMapping("/add-cage")
    public ResponseEntity<Object> addCage(
            @RequestParam CageSize cageSize,
            @RequestParam double pricePerDay
    ) {
        if (pricePerDay <= 0) {
            return ResponseEntity.badRequest().body("Цена за день должна быть больше нуля");
        }

        Cage newCage = new Cage();
        newCage.setCageSize(cageSize);
        newCage.setPricePerDay(pricePerDay);
        newCage.setCageStatus(CageStatus.FREE); // Assuming the default status is AVAILABLE
        newCage.setLastCleaningTime(LocalDateTime.now()); // Assuming the cage is clean when added
        newCage = cageRepository.save(newCage);

        return ResponseEntity.ok(newCage);
    }

    @PutMapping("/update-cage")
    public ResponseEntity<Object> updateCage(
            @RequestParam long id,
            @RequestParam double pricePerDay
    ) {
        if (pricePerDay <= 0) {
            return ResponseEntity.badRequest().body("Цена за день должна быть больше нуля");
        }

        Cage cage = cageRepository.findById(id).orElse(null);
        if (cage == null) {
            return ResponseEntity.badRequest().body("Нет клетки с таким id");
        }

        cage.setPricePerDay(pricePerDay);
        cage = cageRepository.save(cage);

        return ResponseEntity.ok(cage);
    }

    @DeleteMapping("/delete-cage")
    public ResponseEntity<Object> deleteCage(@RequestParam long id) {
        Cage cage = cageRepository.findById(id).orElse(null);
        if (cage == null) {
            return ResponseEntity.badRequest().body("Нет клетки с таким id");
        }

        if (cage.getCageStatus() != CageStatus.FREE) {
            return ResponseEntity.badRequest().body("Клетка не свободна, её нельзя удалить");
        }

        cageRepository.delete(cage);
        return ResponseEntity.ok().body("Клетка успешно удалена");
    }

    @PostMapping("/add-user")
    public ResponseEntity<Object> addUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam long employeeId
    ) {
        if (username.isEmpty() || username.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Username must be at least 2 characters long");
        }

        if (userRepository.existsByUsername(username.trim())) {
            return ResponseEntity.badRequest().body("A user with this username already exists");
        }

        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            return ResponseEntity.badRequest().body("No employee found with this ID");
        }
        String role = "";
        if(employee.getJob() == Job.VET){
            role = "VET";
        }else if(employee.getJob() == INPATIENT){
            role = "INPATIENT";
        }else if(employee.getJob() == ADMINISTRATOR){
            role = "ADMINISTRATOR";
        }
        userService.registerNewUser(username, password, role, employee);

        User user = userRepository.findByUsername(username).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-user")
    public ResponseEntity<Object> updateUser(
            @RequestParam long id,
            @RequestParam String username
    ) {
        if (username.isEmpty() || username.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Username must be at least 2 characters long");
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("No user found with this ID");
        }

        if (!user.getUsername().equals(username.trim()) && userRepository.existsByUsername(username.trim())) {
            return ResponseEntity.badRequest().body("A user with this username already exists");
        }

        user.setUsername(username.trim());
        user = userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Object> changePassword(
            @RequestParam long id,
            @RequestParam String password
    ) {
        if (password.isEmpty() || password.trim().length() < 6) {
            return ResponseEntity.badRequest().body("Пароль должен быть не короче 6 символов");
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Пользователь с таким id не найден");
        }

        user.setPassword(passwordEncoder.encode(password.trim()));
        user = userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete-user")
    @Transactional
    public ResponseEntity<Object> deleteUser(@RequestParam long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Пользователь с таким id не найден");
        }

        Authority authority = authorityRepository.findByUser(user);
        if(authority != null){
            authorityRepository.delete(authority);
        }
        userRepository.delete(user);
        return ResponseEntity.ok().body("Пользователь успешно удален");
    }
    @PostMapping("/add-diagnosis")
    public ResponseEntity<Object> addDiagnosis(@RequestParam String name) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Название диагноза должно быть длиннее");
        }

        Diagnosis newDiagnosis = new Diagnosis();
        newDiagnosis.setName(name.trim());
        newDiagnosis = diagnosisRepository.save(newDiagnosis);
        return ResponseEntity.ok(newDiagnosis);
    }

    @PutMapping("/update-diagnosis")
    public ResponseEntity<Object> updateDiagnosis(@RequestParam long id, @RequestParam String name) {
        if (name.isEmpty() || name.trim().length() < 2) {
            return ResponseEntity.badRequest().body("Название диагноза должно быть длиннее");
        }

        Diagnosis diagnosis = diagnosisRepository.findById(id).orElse(null);
        if (diagnosis == null) {
            return ResponseEntity.badRequest().body("Нет диагноза с таким id");
        }

        diagnosis.setName(name.trim());
        diagnosis = diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok(diagnosis);
    }

    @DeleteMapping("/delete-diagnosis")
    public ResponseEntity<Object> deleteDiagnosis(@RequestParam long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElse(null);
        if (diagnosis == null) {
            return ResponseEntity.badRequest().body("Нет диагноза с таким id");
        }

        diagnosisRepository.delete(diagnosis);
        return ResponseEntity.ok().body("Диагноз успешно удален");
    }

}
