package com.medicalappointment.ui;

import com.medicalappointment.model.Appointment;
import com.medicalappointment.model.DoctorInfo;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.DoctorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentFrame_Patient extends JFrame {
    private AppointmentService appointmentService = new AppointmentService();
    private DoctorService doctorService = new DoctorService();

    private int patientId;
    private DefaultTableModel tableModel;

    public AppointmentFrame_Patient(int patientId) {
        this.patientId = patientId;
        setTitle("我的预约 - 病人界面");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 表格
        String[] columns = {"ID", "医生", "时间", "状态"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);

        // 预约按钮
        JButton addBtn = new JButton("预约医生");
        JButton cancelBtn = new JButton("取消选中预约");

        addBtn.addActionListener(e -> doAppointment());
        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String status = tableModel.getValueAt(row, 3).toString();
                if (!status.equals("pending")) {
                    JOptionPane.showMessageDialog(this, "只能取消待处理的预约");
                    return;
                }
                int apptId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (appointmentService.delete(apptId)) {
                    JOptionPane.showMessageDialog(this, "取消成功");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "取消失败");
                }
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(cancelBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Appointment> list = appointmentService.getForPatient(patientId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Appointment a : list) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getDoctorName(),
                    sdf.format(a.getTime()),
                    a.getStatus()
            });
        }
    }

    private void doAppointment() {
        List<DoctorInfo> doctors = doctorService.getAllDoctors();
        String[] doctorNames = new String[doctors.size()];
        for (int i = 0; i < doctors.size(); i++) {
            doctorNames[i] = doctors.get(i).getName() + "（" + doctors.get(i).getDepartment() + "）";
        }

        JComboBox<String> doctorCombo = new JComboBox<>(doctorNames);
        JTextField timeField = new JTextField("2025-06-20 15:30");

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("选择医生:")); panel.add(doctorCombo);
        panel.add(new JLabel("预约时间(yyyy-MM-dd HH:mm):")); panel.add(timeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "预约医生", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int selectedIndex = doctorCombo.getSelectedIndex();
                int doctorId = doctors.get(selectedIndex).getDoctorId();
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeField.getText());

                Appointment appt = new Appointment();
                appt.setDoctorId(doctorId);
                appt.setPatientId(patientId);
                appt.setTime(date);
                appt.setStatus("pending");

                if (appointmentService.create(appt)) {
                    JOptionPane.showMessageDialog(this, "预约成功，等待医生确认");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "预约失败");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误：" + ex.getMessage());
            }
        }
    }
}
