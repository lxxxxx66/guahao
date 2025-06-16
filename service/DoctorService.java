package com.medicalappointment.service;

import com.medicalappointment.dao.DoctorDAO;
import com.medicalappointment.model.DoctorInfo;

import java.util.List;

public class DoctorService {
    private DoctorDAO dao = new DoctorDAO();

    public List<DoctorInfo> getAllDoctors() {
        return dao.findAll();
    }

    public boolean addDoctor(String username, String password, DoctorInfo doctor) {
        return dao.insert(username, password, doctor);
    }

    public boolean deleteDoctor(int doctorId) {
        return dao.delete(doctorId);
    }
}
