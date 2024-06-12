package dev.university.degree.controllers;

import dev.university.degree.entities.*;
import dev.university.degree.repositories.*;
import dev.university.degree.util.AppointmentStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/vet")
public class VetController {
    AppointmentRepository appointmentRepository;
    ProcedureRepository procedureRepository;
    MedicationStorageRepository medicationStorageRepository;
    AppointmentProcedureRepository appointmentProcedureRepository;
    JournalRepository journalRepository;
    ReceiptRepository receiptRepository;

    public VetController(
            AppointmentRepository appointmentRepository,
            ProcedureRepository procedureRepository,
            MedicationStorageRepository medicationStorageRepository,
            AppointmentProcedureRepository appointmentProcedureRepository,
            JournalRepository journalRepository,
            ReceiptRepository receiptRepository
    ){
        this.appointmentRepository = appointmentRepository;
        this.procedureRepository = procedureRepository;
        this.medicationStorageRepository = medicationStorageRepository;
        this.appointmentProcedureRepository = appointmentProcedureRepository;
        this.journalRepository = journalRepository;
        this.receiptRepository = receiptRepository;
    }

    @GetMapping({"/", ""})
    public String index(Model model){
        List<Appointment> appointmentList = appointmentRepository.findAllByStatus(AppointmentStatus.WAITING);
        List<Appointment> recentAppointmentList = appointmentRepository.findAllByStatus(AppointmentStatus.WAITING)
                .stream().limit(10).toList();
        model.addAttribute("appointments", appointmentList);
        model.addAttribute("recentAppointments", recentAppointmentList);
        return "vet/appointments";
    }

    @GetMapping("/{id}")
    public String appointment(@PathVariable Long id, Model model){
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if(appointmentOptional.isEmpty()){
            return "redirect:/vet?error=no-appointment";
        }
        Appointment appointment = appointmentOptional.get();
        if(appointment.getStatus() != AppointmentStatus.WAITING){
            return "redirect:/vet";
        }
        appointment.setStatus(AppointmentStatus.IN_PROCESS);
        model.addAttribute("appointment", appointmentOptional.get());
        model.addAttribute("procedures", procedureRepository.findAll());
        model.addAttribute("medications", medicationStorageRepository.findAll());
        return "vet/appointment";
    }

    @PostMapping("/appointment-receipt")
    @Transactional
    public String appointmentReceipt(HttpServletRequest httpServletRequest){
        String diagnosis = httpServletRequest.getParameter("diagnosis").trim();
        String prescription = httpServletRequest.getParameter("prescription").trim();
        String comment = httpServletRequest.getParameter("comment").trim();

        long appointmentId = Long.parseLong(httpServletRequest.getParameter("appointment_id"));
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if(appointment == null){
            return "redirect:/vet";
        }
        Journal newJournal = new Journal();
        newJournal.setAppointment(appointment);
        newJournal.setDiagnosis(diagnosis);
        newJournal.setPrescription(prescription);
        newJournal.setComment(comment);
        journalRepository.save(newJournal);
        int amount = Integer.parseInt(httpServletRequest.getParameter("procedures_amount"));

        List<AppointmentProcedures> appointmentProceduresList = new ArrayList<>();
        for(int procedure_number = 1; procedure_number <= amount; procedure_number++){
            if(httpServletRequest.getParameter("procedure_id_" + procedure_number) == null){
                continue;
            }

            Long procedureId = Long.parseLong(
                    httpServletRequest.getParameter("procedure_id_" + procedure_number)
            );

            Long medicationStorageId = Long.parseLong(
                    httpServletRequest.getParameter("procedure_medication_id_" + procedure_number)
            );
            System.out.println(httpServletRequest.getParameter("procedure_id_" + procedure_number));
            System.out.println(httpServletRequest.getParameter("procedure_medication_id_" + procedure_number));
            System.out.println(httpServletRequest.getParameter("procedure_medication_amount_" + procedure_number));
            int medicationAmount = Integer.parseInt(
                    httpServletRequest.getParameter("procedure_medication_amount_" + procedure_number)
            );

            Procedure procedure = procedureRepository.findById(procedureId).orElse(null);
            MedicationStorage medicationStorage = medicationStorageRepository.findById(medicationStorageId).orElse(null);
            if(medicationStorage == null || medicationStorage.getAmount() - amount < 0){
                System.out.println();
                return "redirect:/vet/" + httpServletRequest.getParameter("appointment_id");
            }

            AppointmentProcedures appointmentProcedures = new AppointmentProcedures();
            appointmentProcedures.setAppointment(appointment);
            appointmentProcedures.setProcedure(procedure);
            appointmentProcedures.setMedicationStorage(medicationStorage);
            appointmentProcedures.setMedicationAmount(medicationAmount);
            appointmentProceduresList.add(appointmentProcedureRepository.save(appointmentProcedures));

            medicationStorage.setAmount(medicationStorage.getAmount() - medicationAmount);
            medicationStorageRepository.save(medicationStorage);
        }

        double receiptSum = 0;
        for (AppointmentProcedures appointmentProcedures : appointmentProceduresList) {
            receiptSum += appointmentProcedures.getMedicationStorage().getSupplyPrice() +
                    (appointmentProcedures.getMedicationStorage().getSupplyPrice() / 100 * 20);
        }

        Receipt receipt = new Receipt();
        receipt.setSum(receiptSum);
        receipt.setAppointment(appointment);
        receiptRepository.save(receipt);

        appointment.setStatus(AppointmentStatus.FINISHED);
        appointmentRepository.save(appointment);
        return "redirect:/vet";
    }
}
