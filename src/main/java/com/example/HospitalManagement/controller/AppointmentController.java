package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.model.Appointment;
import com.example.HospitalManagement.service.AppointmentService;
import com.example.HospitalManagement.service.DoctorService;
import com.example.HospitalManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

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

    @PostMapping
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        // Here, you'd need to handle the logic to extract patientId and doctorId from appointment
        // before passing it to service layer.
        appointmentService.addAppointment(appointment.getPatientId(), appointment.getDoctorId(),
                appointment.getAppointmentDate(), appointment.getReason());
        return "redirect:/appointments";
    }

    @PostMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments"; // Redirect to appointments list
    }
}