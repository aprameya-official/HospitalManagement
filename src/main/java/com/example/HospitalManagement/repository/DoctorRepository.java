package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}