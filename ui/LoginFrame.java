package com.medicalappointment.ui;

import com.medicalappointment.model.User;
import com.medicalappointment.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService = new UserService();

    public LoginFrame() {
        setTitle("医疗预约管理系统 - 登录");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(140, 50, 200, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 90, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(140, 90, 200, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(140, 130, 100, 30);
        panel.add(loginButton);

        getContentPane().add(panel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
    }

    private void loginAction() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = userService.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "登录成功，欢迎 " + user.getRole());
            // 可以跳转到不同主界面
            this.dispose(); // 关闭登录窗口
            new MainFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
