package com.medicalappointment.service;

import com.medicalappointment.dao.AppointmentDAO;
import com.medicalappointment.model.Appointment;

import java.util.List;

public class AppointmentService {
    private AppointmentDAO dao = new AppointmentDAO();

    public boolean create(Appointment a) {
        return dao.addAppointment(a);
    }

    public List<Appointment> getForPatient(int patientId) {
        return dao.findByPatient(patientId);
    }

    public List<Appointment> getForDoctor(int doctorId) {
        return dao.findByDoctor(doctorId);
    }

    public boolean updateStatus(int apptId, String status) {
        return dao.updateStatus(apptId, status);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
    public List<Appointment> getAllAppointments() {
        return dao.findAll();
    }
}
