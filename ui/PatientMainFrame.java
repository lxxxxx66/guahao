package com.medicalappointment.ui;

import com.medicalappointment.model.User;

import javax.swing.*;
import java.awt.*;

public class PatientMainFrame extends JFrame {
    private User user;

    public PatientMainFrame(User user) {
        this.user = user;
        setTitle("病人主界面 - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnMyAppointments = new JButton("我的预约");
        btnMyAppointments.addActionListener(e -> new AppointmentFrame_Patient(user.getUserId()).setVisible(true));

        JPanel panel = new JPanel();
        panel.add(btnMyAppointments);

        add(panel);
    }
}
