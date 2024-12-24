package com.example.HospitalManagement.service;

import com.example.HospitalManagement.model.Patient;
import com.example.HospitalManagement.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    // Add this method for adding a new patient with name, age, and gender
    public void addPatient(String name, int age, String gender, String contactNumber) {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setContactNumber(contactNumber);
        patientRepository.save(patient);
    }
    public void updatePatient(Long id, String name, int age, String gender, String contactNumber) {
        Patient patient = patientRepository.findById(id).orElse(null);

        if (patient != null) {
            patient.setName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setContactNumber(contactNumber);  // Update contact number
            patientRepository.save(patient);
        }
    }
}