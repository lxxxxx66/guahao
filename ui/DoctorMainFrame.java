package com.medicalappointment.ui;

import com.medicalappointment.model.User;

import javax.swing.*;
import java.awt.*;

public class DoctorMainFrame extends JFrame {
    private User user;

    public DoctorMainFrame(User user) {
        this.user = user;
        setTitle("医生主界面 - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnManageAppointments = new JButton("预约管理");
        btnManageAppointments.addActionListener(e -> new AppointmentFrame_Doctor(user.getUserId()).setVisible(true));

        JPanel panel = new JPanel();
        panel.add(btnManageAppointments);

        add(panel);
    }
}
