package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}