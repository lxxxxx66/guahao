package com.medicalappointment.ui;

import com.medicalappointment.model.PatientInfo;
import com.medicalappointment.service.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientManageFrame extends JFrame {
    private PatientService service = new PatientService();
    private DefaultTableModel tableModel;

    public PatientManageFrame() {
        setTitle("病人管理");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 表格
        String[] columns = {"ID", "姓名", "年龄", "性别"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);

        // 按钮
        JButton addBtn = new JButton("新增病人");
        JButton delBtn = new JButton("删除选中病人");

        addBtn.addActionListener(e -> addPatient());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int patientId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (service.deletePatient(patientId)) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    refreshTable();
                }
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<PatientInfo> patients = service.getAllPatients();
        for (PatientInfo p : patients) {
            tableModel.addRow(new Object[]{p.getPatientId(), p.getName(), p.getAge(), p.getGender()});
        }
    }

    private void addPatient() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("登录用户名:")); panel.add(usernameField);
        panel.add(new JLabel("密码:")); panel.add(passwordField);
        panel.add(new JLabel("姓名:")); panel.add(nameField);
        panel.add(new JLabel("年龄:")); panel.add(ageField);
        panel.add(new JLabel("性别:")); panel.add(genderField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新增病人", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            PatientInfo patient = new PatientInfo();
            patient.setName(nameField.getText());
            patient.setAge(Integer.parseInt(ageField.getText()));
            patient.setGender(genderField.getText());

            boolean success = service.addPatient(usernameField.getText(), passwordField.getText(), patient);
            if (success) {
                JOptionPane.showMessageDialog(this, "添加成功");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "添加失败");
            }
        }
    }
}
