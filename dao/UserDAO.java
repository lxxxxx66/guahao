package com.medicalappointment.dao;

import com.medicalappointment.model.User;
import com.medicalappointment.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
