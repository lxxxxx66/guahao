package com.medicalappointment.ui;

import com.medicalappointment.model.DoctorInfo;
import com.medicalappointment.service.DoctorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DoctorManagementFrame extends JFrame {
    private DoctorService service = new DoctorService();
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, deptField, titleField, usernameField, passwordField;

    public DoctorManagementFrame() {
        setTitle("医生管理");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadDoctors();
    }

    private void initUI() {
        model = new DefaultTableModel(new String[]{"ID", "姓名", "科室", "职称"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        nameField = new JTextField();
        deptField = new JTextField();
        titleField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JTextField();

        formPanel.add(new JLabel("姓名:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("科室:"));
        formPanel.add(deptField);
        formPanel.add(new JLabel("职称:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("用户名:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("密码:"));
        formPanel.add(passwordField);

        JButton addBtn = new JButton("添加医生");
        JButton deleteBtn = new JButton("删除医生");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

        // 事件绑定
        addBtn.addActionListener(e -> addDoctor());
        deleteBtn.addActionListener(e -> deleteDoctor());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                nameField.setText(model.getValueAt(row, 1).toString());
                deptField.setText(model.getValueAt(row, 2).toString());
                titleField.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    private void loadDoctors() {
        model.setRowCount(0);
        List<DoctorInfo> list = service.getAllDoctors();
        for (DoctorInfo d : list) {
            model.addRow(new Object[]{
                    d.getDoctorId(), d.getName(), d.getDepartment(), d.getTitle()
            });
        }
    }

    private void addDoctor() {
        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();
        String title = titleField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || dept.isEmpty() || title.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段不能为空！");
            return;
        }

        DoctorInfo doctor = new DoctorInfo();
        doctor.setName(name);
        doctor.setDepartment(dept);
        doctor.setTitle(title);

        boolean success = service.addDoctor(username, password, doctor);
        if (success) {
            JOptionPane.showMessageDialog(this, "添加成功！");
            loadDoctors();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "添加失败！");
        }
    }

    private void deleteDoctor() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请选中要删除的医生");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定删除该医生？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteDoctor(id)) {
                JOptionPane.showMessageDialog(this, "删除成功！");
                loadDoctors();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！");
            }
        }
    }

    private void clearForm() {
        nameField.setText("");
        deptField.setText("");
        titleField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoctorManagementFrame().setVisible(true));
    }
}


