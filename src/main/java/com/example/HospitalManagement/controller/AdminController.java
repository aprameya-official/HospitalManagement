package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.model.Appointment;
import com.example.HospitalManagement.model.Patient;
import com.example.HospitalManagement.model.User;
import com.example.HospitalManagement.service.AppointmentService;
import com.example.HospitalManagement.service.PatientService;
import com.example.HospitalManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // Show admin dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("patients", patientService.getAllPatients());
        return "adminDashboard";
    }

    // Manage Users
    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "manage-users";
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.registerUser(username, password, role);
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.updateUser(id, username, password, role);
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/manage-users";
    }

    // Manage Appointments
    @GetMapping("/manage-appointments")
    public String manageAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments";
    }

    @PostMapping("/add-appointment")
    public String addAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam String appointmentDate, @RequestParam String reason) {
        appointmentService.addAppointment(patientId, doctorId, appointmentDate, reason);
        return "redirect:/admin/manage-appointments";
    }

    @GetMapping("/edit-appointment/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "edit-appointment";
    }

    @PostMapping("/update-appointment/{id}")
    public String updateAppointment(@PathVariable Long id, @RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam String appointmentDate, @RequestParam String reason) {
        appointmentService.updateAppointment(id, patientId, doctorId, appointmentDate, reason);
        return "redirect:/admin/manage-appointments";
    }

    @GetMapping("/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/admin/manage-appointments";
    }

    // Manage Patients
    @GetMapping("/manage-patients")
    public String managePatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String name, @RequestParam int age, @RequestParam String gender, @RequestParam String contactNumber) {
        // Call the service method with all 4 required parameters: name, age, gender, contactNumber
        patientService.addPatient(name, age, gender, contactNumber);
        return "redirect:/admin/manage-patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "edit-patient";
    }

    @PostMapping("/update-patient/{id}")
    public String updatePatient(@PathVariable Long id, @RequestParam String name, @RequestParam int age, @RequestParam String gender, @RequestParam String contactNumber) {
        // Update patient with all required fields including contact number
        patientService.updatePatient(id, name, age, gender, contactNumber);
        return "redirect:/admin/manage-patients";
    }

    @GetMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/admin/manage-patients";
    }
}