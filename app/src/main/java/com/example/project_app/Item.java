package com.example.project_app;


import androidx.appcompat.app.AppCompatActivity;

public class Item extends AppCompatActivity {
    private int imageResId;
    private String name;
    private String description;
    private String price;
    private String unit;
    private String qty;

    public Item(int imageResId, String name, String description, String price, String unit, String qty) {
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.qty = qty;
    }

    // Getters and setters for each member variable
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}