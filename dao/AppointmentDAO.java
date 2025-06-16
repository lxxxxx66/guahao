package com.medicalappointment.dao;

import com.medicalappointment.model.Appointment;
import com.medicalappointment.util.DBUtil;

import java.sql.*;
import java.util.*;

public class AppointmentDAO {

    public boolean addAppointment(Appointment appt) {
        // 自己生成排班 ID
        int scheduleId = new Random().nextInt(10000); // 简单示例，实际需根据业务逻辑生成
        appt.setScheduleId(scheduleId);

        String sql = "INSERT INTO appointment (patient_id, doctor_id, schedule_id, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appt.getPatientId());
            stmt.setInt(2, appt.getDoctorId());
            stmt.setInt(3, appt.getScheduleId()); // 新增的字段
            stmt.setTimestamp(4, new Timestamp(appt.getTime().getTime()));
            stmt.setString(5, appt.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appointment> findByPatient(int patientId) {
        return findByRole("patient_id", patientId);
    }

    public List<Appointment> findByDoctor(int doctorId) {
        return findByRole("doctor_id", doctorId);
    }

    private List<Appointment> findByRole(String field, int id) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p.name as pname, d.name as dname FROM appointment a " +
                "JOIN patient_info p ON a.patient_id = p.patient_id " +
                "JOIN doctor_info d ON a.doctor_id = d.doctor_id " +
                "WHERE a." + field + " = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("appointment_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setTime(rs.getTimestamp("appointment_time"));
                a.setStatus(rs.getString("status"));
                a.setPatientName(rs.getString("pname"));
                a.setDoctorName(rs.getString("dname"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointment SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int appointmentId) {
        String sql = "DELETE FROM appointment WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p.name as pname, d.name as dname FROM appointment a " +
                "JOIN patient_info p ON a.patient_id = p.patient_id " +
                "JOIN doctor_info d ON a.doctor_id = d.doctor_id " +
                "ORDER BY a.appointment_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("appointment_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setTime(rs.getTimestamp("appointment_time"));
                a.setStatus(rs.getString("status"));
                a.setPatientName(rs.getString("pname"));
                a.setDoctorName(rs.getString("dname"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}