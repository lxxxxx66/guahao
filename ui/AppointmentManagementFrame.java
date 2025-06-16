package com.medicalappointment.ui;

import com.medicalappointment.model.Appointment;
import com.medicalappointment.service.AppointmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AppointmentManagementFrame extends JFrame {
    private AppointmentService appointmentService = new AppointmentService();
    private DefaultTableModel tableModel;

    public AppointmentManagementFrame() {
        setTitle("管理员预约管理");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"预约ID", "病人", "医生", "预约时间", "状态"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnConfirm = new JButton("确认预约");
        JButton btnReject = new JButton("拒绝预约");
        JButton btnRefresh = new JButton("刷新");

        btnConfirm.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "请选择一条预约记录");
                return;
            }
            String status = tableModel.getValueAt(row, 4).toString();
            if (!"pending".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(this, "只有待处理预约能被确认");
                return;
            }
            int apptId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (appointmentService.updateStatus(apptId, "confirmed")) {
                JOptionPane.showMessageDialog(this, "预约已确认");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "操作失败");
            }
        });

        btnReject.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "请选择一条预约记录");
                return;
            }
            String status = tableModel.getValueAt(row, 4).toString();
            if (!"pending".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(this, "只有待处理预约能被拒绝");
                return;
            }
            int apptId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (appointmentService.updateStatus(apptId, "rejected")) {
                JOptionPane.showMessageDialog(this, "预约已拒绝");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "操作失败");
            }
        });

        btnRefresh.addActionListener(e -> refreshTable());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnConfirm);
        btnPanel.add(btnReject);
        btnPanel.add(btnRefresh);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Appointment> list = appointmentService.getAllAppointments();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Appointment a : list) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getPatientName(),
                    a.getDoctorName(),
                    sdf.format(a.getTime()),
                    a.getStatus()
            });
        }
    }
}
