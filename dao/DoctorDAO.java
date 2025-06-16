package com.medicalappointment.dao;

import com.medicalappointment.model.DoctorInfo;
import com.medicalappointment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public List<DoctorInfo> findAll() {
        List<DoctorInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM doctor_info";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new DoctorInfo(
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("title")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(String username, String password, DoctorInfo doctor) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 插入user表
            String userSql = "INSERT INTO user (username, password, role) VALUES (?, ?, 'doctor')";
            PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, password);
            userStmt.executeUpdate();
            ResultSet rs = userStmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                // 插入doctor_info表
                String docSql = "INSERT INTO doctor_info (doctor_id, name, department, title) VALUES (?, ?, ?, ?)";
                PreparedStatement docStmt = conn.prepareStatement(docSql);
                docStmt.setInt(1, userId);
                docStmt.setString(2, doctor.getName());
                docStmt.setString(3, doctor.getDepartment());
                docStmt.setString(4, doctor.getTitle());
                docStmt.executeUpdate();
                conn.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int doctorId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            return stmt.executeUpdate() > 0; // 级联删除doctor_info
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
