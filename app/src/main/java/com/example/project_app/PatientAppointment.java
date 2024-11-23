package com.example.project_app;

public class PatientAppointment {
    private int appointmentYear;
    private int appointmentMonth;
    private int appointmentDay;
    private String appointmentNote;
    private String appointmentDate;

    public PatientAppointment() {
        // Constructeur vide requis pour Firebase
    }

    public PatientAppointment(int year, int month, int day, String note) {
        this.appointmentYear = year;
        this.appointmentMonth = month;
        this.appointmentDay = day;
        this.appointmentNote = note;
        this.appointmentDate = year + "-" + (month + 1) + "-" + day;
    }

    public int getAppointmentYear() {
        return appointmentYear;
    }

    public void setAppointmentYear(int year) {
        this.appointmentYear = year;
    }

    public int getAppointmentMonth() {
        return appointmentMonth;
    }

    public void setAppointmentMonth(int month) {
        this.appointmentMonth = month;
    }

    public int getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(int day) {
        this.appointmentDay = day;
    }

    public String getAppointmentNote() {
        return appointmentNote;
    }

    public void setAppointmentNote(String note) {
        this.appointmentNote = note;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String date) {
        this.appointmentDate = date;
    }
}