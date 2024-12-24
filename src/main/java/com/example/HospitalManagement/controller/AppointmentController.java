package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.model.Appointment;
import com.example.HospitalManagement.service.AppointmentService;
import com.example.HospitalManagement.service.DoctorService;
import com.example.HospitalManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;  // Correct import for PropertyEditorSupport
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // Custom binding for LocalDateTime
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                setValue(LocalDateTime.parse(text, formatter));
            }
        });
    }

    @GetMapping
    public String getAllAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments"; // View to list all appointments
    }

    @GetMapping("/{id}/edit")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "edit-appointment"; // View to edit an appointment
    }

    @GetMapping("/add-appointment")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "add-appointment"; // View to add a new appointment
    }

    // Method to save a new appointment
    @PostMapping
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.addAppointment(appointment.getPatientId(), appointment.getDoctorId(),
                appointment.getAppointmentDate(), appointment.getReason());
        return "redirect:/appointments";
    }

    // Method to handle updating an appointment
    @PostMapping("/{id}")
    public String updateAppointment(@PathVariable Long id, @ModelAttribute Appointment appointment) {
        // Update the appointment details
        appointmentService.updateAppointment(id, appointment.getPatientId(), appointment.getDoctorId(),
                appointment.getAppointmentDate(), appointment.getReason());
        return "redirect:/appointments"; // Redirect to appointments list
    }

    // Method to delete an appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments"; // Redirect to appointments list
    }
}