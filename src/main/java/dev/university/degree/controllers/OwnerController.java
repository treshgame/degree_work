package dev.university.degree.controllers;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.util.EmployeeStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController{
    private final EmployeeRepository employeeRepository;
    private final SupplierRepository supplierRepository;
    private final MedicationRepository medicationRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyDetailsRepository supplyDetailsRepository;
    private final MedicationStorageRepository medicationStorageRepository;

    public OwnerController(
            EmployeeRepository employeeRepository,
            SupplierRepository supplierRepository,
            MedicationRepository medicationRepository,
            SupplyRepository supplyRepository,
            SupplyDetailsRepository supplyDetailsRepository,
            MedicationStorageRepository medicationStorageRepository
    ){
        this.employeeRepository = employeeRepository;
        this.supplierRepository = supplierRepository;
        this.medicationRepository = medicationRepository;
        this.supplyRepository = supplyRepository;
        this.supplyDetailsRepository = supplyDetailsRepository;
        this.medicationStorageRepository = medicationStorageRepository;
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
    @Transactional
    public String addSupply(HttpServletRequest servletRequest) throws SQLException{
        int amountOfMedications = Integer.parseInt(servletRequest.getParameter("amountOfMedications"));
        Long supplierId = Long.parseLong(servletRequest.getParameter("supplier"));

        System.out.println("-----");
        Supply newSupply = new Supply();
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if(supplier == null){
            System.out.println("Ошибка");
            return "redirect:/owner/add_supply";
        }

        newSupply.setSupplier(supplier);
        newSupply.setDate(LocalDate.now());
        newSupply = supplyRepository.save(newSupply);
        if(newSupply.getId() == null){
            throw new SQLException("Ошибка при добавлении поставки");
        }

        for(int i = 1; i <= amountOfMedications; i++){
            SupplyDetails supplyDetails = new SupplyDetails();
            Long medicationId = Long.parseLong(servletRequest.getParameter("medication_" + i));
            Medication medication = medicationRepository.findById(medicationId).orElse(null);
            if(medication == null){
                continue;
            }
            double price = Double.parseDouble(servletRequest.getParameter("medication_price_" + i));
            int amount = Integer.parseInt(servletRequest.getParameter("medication_amount_" + i));
            supplyDetails.setMedication(medication);
            supplyDetails.setPricePerAmount(price);
            supplyDetails.setAmount(amount);
            supplyDetails.setSupply(newSupply);
            if(supplyDetailsRepository.save(supplyDetails).getId() == null){
                throw new SQLException("Ошибка при добавлении деталей поставки");
            }

            MedicationStorage medicationStorage = medicationStorageRepository.findByMedicationIdAndPrice(
                    medication.getId(),
                    supplyDetails.getPricePerAmount()
            ).orElse(null);

            if(medicationStorage == null){
                medicationStorage = new MedicationStorage();
                medicationStorage.setAmount(0);
                medicationStorage.setMedication(medication);
                medicationStorage.setSupplyPrice(supplyDetails.getPricePerAmount());
            }

            medicationStorage.setAmount(medicationStorage.getAmount() + supplyDetails.getAmount());
            medicationStorageRepository.save(medicationStorage);
        }

        return "redirect:/owner/add_supply";
    }

    @GetMapping("/all_supplies")
    public String allSupplies(Model model){
        List<Supply> supplies = supplyRepository.findAll();
        List<SupplyDetails> supplyDetailsList = supplyDetailsRepository.findAll();
        model.addAttribute("supplies", supplies);
        model.addAttribute("supplyDetails", supplyDetailsList);
        return "owner/all_supplies";
    }

    @GetMapping("/medication_storage")
    public String medicationStorage(Model model){
        List<MedicationStorage> medicationStorages = medicationStorageRepository.findAll();
        model.addAttribute("medicationStorages", medicationStorages);
        return "owner/medication_storage";
    }
}
