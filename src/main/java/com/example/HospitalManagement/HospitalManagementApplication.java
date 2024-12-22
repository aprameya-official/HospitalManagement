package com.example.HospitalManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.example.HospitalManagement", // This will include the base package
    "com.example.HospitalManagement.controller", // Explicitly include the controller package
    "com.example.HospitalManagement.model", // Explicitly include the model package
    "com.example.HospitalManagement.repository", // Explicitly include the repository package
    "com.example.HospitalManagement.service" // Explicitly include the service package
})
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }
}