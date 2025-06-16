package com.medicalappointment.ui;

import com.medicalappointment.model.DoctorInfo;
import com.medicalappointment.model.User;
import com.medicalappointment.service.DoctorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

        // 获取医生信息
        DoctorService doctorService = new DoctorService();
        List<DoctorInfo> doctors = doctorService.getAllDoctors();

        // 创建文本区域显示医生信息
        JTextArea doctorInfoArea = new JTextArea();
        doctorInfoArea.setEditable(false);
        for (DoctorInfo doctor : doctors) {
            doctorInfoArea.append("姓名: " + doctor.getName() + ", 科室: " + doctor.getDepartment() + ", 职称: " + doctor.getTitle() + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(doctorInfoArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(btnMyAppointments, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }
}