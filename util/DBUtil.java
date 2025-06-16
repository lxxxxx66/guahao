package com.medicalappointment.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_appointment?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "000000" ;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8.x 以上驱动
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
