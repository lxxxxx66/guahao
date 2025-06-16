package com.medicalappointment.model;

public class DoctorInfo {
    private int doctorId;
    private String name;
    private String department;
    private String title;

    public DoctorInfo() {}

    public DoctorInfo(int doctorId, String name, String department, String title) {
        this.doctorId = doctorId;
        this.name = name;
        this.department = department;
        this.title = title;
    }

    // getter & setter
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
