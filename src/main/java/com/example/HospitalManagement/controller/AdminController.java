package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.model.Appointment;
import com.example.HospitalManagement.model.Patient;
import com.example.HospitalManagement.model.User;
import com.example.HospitalManagement.service.AppointmentService;
import com.example.HospitalManagement.service.PatientService;
import com.example.HospitalManagement.service.UserService;
import com.example.HospitalManagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    // Show admin dashboard
    @GetMapping("/dashboard")  // Fixed the route to '/dashboard'
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "adminDashboard";  // Ensure the template file is named 'adminDashboard.html'
    }

    // Manage Users
    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "manage-users";  // Ensure this template exists
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.registerUser(username, password, role);
        return "redirect:/admin/manage-users";
    }

    // Manage Appointments
    @GetMapping("/manage-appointments")
    public String manageAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments";  // Ensure this template exists
    }

    @PostMapping("/add-appointment")
    public String addAppointment(@RequestParam Long patientId,
                                 @RequestParam Long doctorId,
                                 @RequestParam String appointmentDate,
                                 @RequestParam String reason) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);

        appointmentService.addAppointment(patientId, doctorId, date, reason);
        return "redirect:/admin/manage-appointments";
    }

    // Manage Patients
    @GetMapping("/manage-patients")
    public String managePatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";  // Ensure this template exists
    }

    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String name,
                             @RequestParam int age,
                             @RequestParam String gender,
                             @RequestParam String contactNumber) {
        patientService.addPatient(name, age, gender, contactNumber);
        return "redirect:/admin/manage-patients";
    }

    // Other CRUD operations for users, appointments, and patients remain the same
}