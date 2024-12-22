package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.model.Doctor;
import com.example.HospitalManagement.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Get all doctors
    @GetMapping
    public String getAllDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctors"; // Maps to doctors.html in templates
    }

    // Show the form to add a new doctor
    @GetMapping("/adddoctor")
    public String addDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "add-doctor"; // Maps to add-doctor.html in templates
    }

    // Save the doctor
    @PostMapping
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors"; // Redirects to the list of doctors after saving
    }

    // Show the form to edit an existing doctor
    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        model.addAttribute("doctor", doctor);
        return "edit-doctor"; // Maps to edit-doctor.html in templates
    }

    // Update the doctor details
    @PostMapping("/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
        doctor.setId(id);
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors"; // Redirects to the list of doctors after update
    }

    // Delete a doctor
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors"; // Redirects to the list of doctors after deletion
    }

}