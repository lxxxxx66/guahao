package com.medicalappointment.ui;

import com.medicalappointment.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private User user;

    public AdminMainFrame(User user) {
        this.user = user;
        setTitle("管理员主界面 - " + user.getUsername());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建按钮
        JButton btnManageDoctors = new JButton("医生管理");
        JButton btnManagePatients = new JButton("病人管理");
        JButton btnManageAppointments = new JButton("预约管理");
        JButton btnRefresh = new JButton("刷新");
        JButton btnLogout = new JButton("退出登录");

        // 设置按钮动作
        btnManageDoctors.addActionListener(e -> {
            // 打开医生管理界面
            new DoctorManagementFrame().setVisible(true);
        });

        btnManagePatients.addActionListener(e -> {
            // 打开病人管理界面
            new PatientManageFrame().setVisible(true);
        });

        btnManageAppointments.addActionListener(e -> {
            // 打开预约管理界面
            new AppointmentManagementFrame().setVisible(true);
        });

        btnRefresh.addActionListener(e -> {
            // 刷新操作，具体可扩展
            JOptionPane.showMessageDialog(this, "刷新完成！");
        });

        btnLogout.addActionListener(e -> {
            dispose();
            // 返回登录界面
            // 假设Main类有显示登录窗方法
            com.medicalappointment.Main.main(null);
        });

        // 布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panel.add(btnManageDoctors);
        panel.add(btnManagePatients);
        panel.add(btnManageAppointments);
        panel.add(btnRefresh);
        panel.add(btnLogout);

        add(panel);
    }
}
