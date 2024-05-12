package dev.university.degree.controllers;

import dev.university.degree.entities.Employee;
import dev.university.degree.entities.Medication;
import dev.university.degree.entities.Supplier;
import dev.university.degree.repositories.EmployeeRepository;
import dev.university.degree.repositories.MedicationRepository;
import dev.university.degree.repositories.SupplierRepository;
import dev.university.degree.util.EmployeeStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
@RequestMapping("/owner")
public class OwnerController{
    private final EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;
    private final MedicationRepository medicationRepository;

    public OwnerController(
            EmployeeRepository employeeRepository,
            SupplierRepository supplierRepository,
            MedicationRepository medicationRepository
    ){
        this.employeeRepository = employeeRepository;
        this.supplierRepository = supplierRepository;
        this.medicationRepository = medicationRepository;
    }

    @GetMapping({"", "/"})
    public String index(){
        return "owner/owner_index";
    }

    @GetMapping("/add_employee")
    public String addEmployeePage(Model model){
        model.addAttribute("employee", new Employee());
        return "owner/add_employee";
    }

    @PostMapping("/add_employee")
    public String addEmployee(@ModelAttribute("employee") Employee employee){
        employee.setJobStart(LocalDate.now());
        employee.setStatus(EmployeeStatus.EMPLOYED);
        employeeRepository.save(employee);
        return "redirect:/owner";
    }

    @GetMapping("/add_supplier")
    public String addSupplierPage(Model model){
        model.addAttribute("supplier", new Supplier());
        return "owner/add_supplier";
    }

    @PostMapping("/add_supplier")
    public String addSupplier(@ModelAttribute("supplier") Supplier supplier){
        supplierRepository.save(supplier);
        return "redirect:/owner";
    }

    @GetMapping("/add_medication")
    public String addMedicationPage(Model model){
        model.addAttribute("medication", new Medication());
        return "owner/add_medication";
    }

    @PostMapping("/add_medication")
    public String addMedication(@ModelAttribute("medication") Medication medication){
        medicationRepository.save(medication);
        return "redirect:/owner/add_medication";
    }

    //Сделать систему поставок
    @GetMapping("/add_supply")
    public String addSupplyPage(Model model){
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("medications", medicationRepository.findAll());
        return "owner/add_supply";
    }

    @PostMapping("/add_supply")
    public String addSupply(HttpServletRequest servletRequest){
        System.out.println("Servlet request:");

        servletRequest.getParameterMap().entrySet().forEach((stringEntry -> {
            System.out.println(stringEntry.getKey() + " " + Arrays.toString(stringEntry.getValue()));
        }));
        return "redirect:/owner/add_supply";
    }

    @GetMapping("/all_supplies")
    public String allSupplies(){
        return "owner/add_supply";
    }
}
