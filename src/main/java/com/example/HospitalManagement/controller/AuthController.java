package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String login() {
        return "login";  // Login page (login.html)
    }

    // Show admin dashboard
    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";  // Make sure this matches the name of your HTML file (e.g., admin_dashboard.html)
    }

    // Show register page
    @GetMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "passwordsDoNotMatch", required = false) String passwordsDoNotMatch,
                           Model model) {
        // Add error messages to the model to display them on the register page
        if (error != null) {
            model.addAttribute("error", "User already exists");
        }
        if (passwordsDoNotMatch != null) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match. Please check your input.");
        }
        return "register";  // Register page (register.html)
    }

    // Handle registration logic
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                               @RequestParam String confirmPassword, @RequestParam String role,
                               RedirectAttributes redirectAttributes) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            // Redirect to register page with error message if passwords don't match
            redirectAttributes.addFlashAttribute("passwordsDoNotMatch", true);
            return "redirect:/register";
        }

        try {
            // Register user and catch exception if the user already exists
            userService.registerUser(username, password, role);
            return "redirect:/login?registered";  // Redirect to login page after successful registration
        } catch (Exception e) {
            // Catch user already exists error and return to register page
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/register";
        }
    }
}