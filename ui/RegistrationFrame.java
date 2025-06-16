package com.medicalappointment.ui;

import com.medicalappointment.model.PatientInfo;
import com.medicalappointment.model.User;
import com.medicalappointment.service.PatientService;
import com.medicalappointment.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> roleComboBox;
    private UserService userService = new UserService();
    private PatientService patientService = new PatientService();

    public RegistrationFrame() {
        setTitle("医疗预约管理系统 - 注册");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(50, 30, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(140, 30, 200, 25);
        panel.add(nameField);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 70, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(140, 70, 200, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(140, 110, 200, 25);
        panel.add(passwordField);

        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setBounds(50, 150, 80, 25);
        panel.add(ageLabel);

        ageField = new JTextField(20);
        ageField.setBounds(140, 150, 200, 25);
        panel.add(ageField);

        JLabel genderLabel = new JLabel("性别:");
        genderLabel.setBounds(50, 190, 80, 25);
        panel.add(genderLabel);

        // 修改性别选项为 F 和 M
        String[] genders = {"F", "M"};
        genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBounds(140, 190, 200, 25);
        panel.add(genderComboBox);

        JLabel roleLabel = new JLabel("角色:");
        roleLabel.setBounds(50, 230, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"patient", "doctor"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(140, 230, 200, 25);
        panel.add(roleComboBox);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(140, 270, 100, 30);
        panel.add(registerButton);

        getContentPane().add(panel);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerAction();
            }
        });
    }

    private void registerAction() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String ageStr = ageField.getText().trim();
        String gender = (String) genderComboBox.getSelectedItem();
        String role = (String) roleComboBox.getSelectedItem();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "姓名、用户名、密码和年龄不能为空");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "年龄必须是数字");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        PatientInfo patient = new PatientInfo();
        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);

        boolean success;
        if ("patient".equalsIgnoreCase(role)) {
            success = patientService.addPatient(username, password, patient);
        } else {
            success = userService.register(user);
            // 这里可以添加医生信息保存逻辑，假设后续有 DoctorInfo 类和相应的服务
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "注册成功，请登录");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "注册失败，请重试");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistrationFrame().setVisible(true);
        });
    }
}