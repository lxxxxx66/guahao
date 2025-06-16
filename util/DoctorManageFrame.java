package com.medicalappointment.ui;

import com.medicalappointment.model.DoctorInfo;
import com.medicalappointment.service.DoctorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorManageFrame extends JFrame {
    private DoctorService service = new DoctorService();
    private DefaultTableModel tableModel;

    public DoctorManageFrame() {
        setTitle("医生管理");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 表格
        String[] columns = {"ID", "姓名", "科室", "职称"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);

        // 按钮
        JButton addBtn = new JButton("新增医生");
        JButton delBtn = new JButton("删除选中医生");

        addBtn.addActionListener(e -> addDoctor());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int doctorId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (service.deleteDoctor(doctorId)) {
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
        tableModel.setRowCount(0); // 清空
        List<DoctorInfo> doctors = service.getAllDoctors();
        for (DoctorInfo d : doctors) {
            tableModel.addRow(new Object[]{d.getDoctorId(), d.getName(), d.getDepartment(), d.getTitle()});
        }
    }

    private void addDoctor() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField titleField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("登录用户名:")); panel.add(usernameField);
        panel.add(new JLabel("密码:")); panel.add(passwordField);
        panel.add(new JLabel("医生姓名:")); panel.add(nameField);
        panel.add(new JLabel("所属科室:")); panel.add(deptField);
        panel.add(new JLabel("职称:")); panel.add(titleField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新增医生", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DoctorInfo doctor = new DoctorInfo();
            doctor.setName(nameField.getText());
            doctor.setDepartment(deptField.getText());
            doctor.setTitle(titleField.getText());

            boolean success = service.addDoctor(usernameField.getText(), passwordField.getText(), doctor);
            if (success) {
                JOptionPane.showMessageDialog(this, "添加成功");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "添加失败");
            }
        }
    }
}
