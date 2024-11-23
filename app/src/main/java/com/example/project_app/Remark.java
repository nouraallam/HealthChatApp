package com.example.project_app;

import androidx.annotation.NonNull;
import com.google.firebase.database.PropertyName;

public class Remark {
    private int year;
    private int month;
    private int dayOfMonth;
    private String remark;
    private String date;

    public Remark() {
        // Constructeur vide requis pour Firebase
    }

    public Remark(int year, int month, int dayOfMonth, String remark) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.remark = remark;
        this.date = year + "-" + (month + 1) + "-" + dayOfMonth;
    }

    @NonNull
    @PropertyName("year")
    public int getYear() {
        return year;
    }

    @PropertyName("year")
    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    @PropertyName("month")
    public int getMonth() {
        return month;
    }

    @PropertyName("month")
    public void setMonth(int month) {
        this.month = month;
    }

    @NonNull
    @PropertyName("dayOfMonth")
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    @PropertyName("dayOfMonth")
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @NonNull
    @PropertyName("remark")
    public String getRemark() {
        return remark;
    }

    @PropertyName("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @NonNull
    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(String date) {
        this.date = date;
    }
}
