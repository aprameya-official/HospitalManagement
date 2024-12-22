package com.example.HospitalManagement.service;

import com.example.HospitalManagement.model.Appointment;
import com.example.HospitalManagement.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public void addAppointment(Long patientId, Long doctorId, String appointmentDate, String reason) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setReason(reason);
        appointmentRepository.save(appointment);
    }

    public void updateAppointment(Long id, Long patientId, Long doctorId, String appointmentDate, String reason) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setReason(reason);
            appointmentRepository.save(appointment);
        }
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}