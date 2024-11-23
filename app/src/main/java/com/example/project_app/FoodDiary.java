package com.example.project_app;

public class FoodDiary {
    private String fullName;
    private String phoneNumber;
    private String date;
    private String repas;
    private String aliments;
    private String quantite;
    private String valeursNutritionnelles;
    private String note;

    // Constructeur par défaut
    public FoodDiary() {
        // Constructeur vide requis pour Firebase
    }

    // Constructeur avec tous les champs
    public FoodDiary(String fullName, String phoneNumber, String date, String repas, String aliments, String quantite, String valeursNutritionnelles, String note) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.repas = repas;
        this.aliments = aliments;
        this.quantite = quantite;
        this.valeursNutritionnelles = valeursNutritionnelles;
        this.note = note;
    }

    // Getters et setters pour chaque champ

    public String getfullName() {
        return fullName;
    }
    public void setfullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepas() {
        return repas;
    }

    public void setRepas(String repas) {
        this.repas = repas;
    }

    public String getAliments() {
        return aliments;
    }

    public void setAliments(String aliments) {
        this.aliments = aliments;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getValeursNutritionnelles() {
        return valeursNutritionnelles;
    }

    public void setValeursNutritionnelles(String valeursNutritionnelles) {
        this.valeursNutritionnelles = valeursNutritionnelles;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}