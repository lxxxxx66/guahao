package com.medicalappointment.model;

import java.util.Date;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private int scheduleId;  // 新增的排班ID字段
    private Date time;
    private String status;

    private String patientName;
    private String doctorName;

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
}
