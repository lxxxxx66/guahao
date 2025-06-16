package com.medicalappointment.dao;

import com.medicalappointment.model.PatientInfo;
import com.medicalappointment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public List<PatientInfo> findAll() {
        List<PatientInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM patient_info";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PatientInfo(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(String username, String password, PatientInfo patient) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 插入user表
            String userSql = "INSERT INTO user (username, password, role) VALUES (?, ?, 'patient')";
            PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, password);
            userStmt.executeUpdate();
            ResultSet rs = userStmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);

                // 插入patient_info
                String patSql = "INSERT INTO patient_info (patient_id, name, age, gender) VALUES (?, ?, ?, ?)";
                PreparedStatement patStmt = conn.prepareStatement(patSql);
                patStmt.setInt(1, userId);
                patStmt.setString(2, patient.getName());
                patStmt.setInt(3, patient.getAge());
                patStmt.setString(4, patient.getGender());
                patStmt.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int patientId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            return stmt.executeUpdate() > 0; // 级联删除 patient_info
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
