package com.example.project_app;

import com.google.firebase.database.Exclude;

public class DoctorCartItem {
    private Product product;
    private int quantity;

    public DoctorCartItem() {
        // Constructeur vide nécessaire pour Firebase
    }

    public DoctorCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Méthode d'exclusion pour ignorer les données inutiles lors de la sauvegarde dans Firebase
    @Exclude
    public double getTotalPrice() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0;
    }
}

