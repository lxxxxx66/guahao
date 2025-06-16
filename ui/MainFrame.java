package com.medicalappointment.ui;

import com.medicalappointment.model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(User user) {
        setTitle("主界面 - " + user.getRole());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("欢迎您，" + user.getUsername() + "（角色：" + user.getRole() + "）", SwingConstants.CENTER);
        add(label);
        if (user.getRole().equals("admin")) {
            JButton doctorBtn = new JButton("医生管理");
            doctorBtn.addActionListener(e -> new com.medicalappointment.ui.DoctorManageFrame().setVisible(true));
            add(doctorBtn, BorderLayout.SOUTH);
        }
        if (user.getRole().equals("admin")) {
            JButton patientBtn = new JButton("病人管理");
            patientBtn.addActionListener(e -> new PatientManageFrame().setVisible(true));
            add(patientBtn, BorderLayout.EAST); // 或其他布局
        }

    }
}
