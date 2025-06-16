package com.medicalappointment.service;

import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.model.PatientInfo;

import java.util.List;

public class PatientService {
    private PatientDAO dao = new PatientDAO();

    public List<PatientInfo> getAllPatients() {
        return dao.findAll();
    }

    public boolean addPatient(String username, String password, PatientInfo patient) {
        return dao.insert(username, password, patient);
    }

    public boolean deletePatient(int patientId) {
        return dao.delete(patientId);
    }
}
