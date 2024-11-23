package com.example.project_app;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private String unit;
    private int qty;

    public Product() {
        // Constructeur par défaut nécessaire pour Firebase
    }

    public Product(String name, String description, double price, String imageUrl, String category, String unit, int qty) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.unit = unit;
        this.qty = qty;
    }

    // Getters et Setters

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void writeToParcel(Parcel dest, int flags) {
        // Écrivez les valeurs de vos champs dans le Parcel
    }

    // Constructeur qui lit les valeurs du Parcel
    protected Product(Parcel in) {
        // Lire les valeurs du Parcel et initialiser vos champs
    }

    // Méthode Parcelable.Creator pour créer une instance de votre classe à partir d'un Parcel
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Autres méthodes Parcelable nécessaires
    @Override
    public int describeContents() {
        return 0;
    }
}
