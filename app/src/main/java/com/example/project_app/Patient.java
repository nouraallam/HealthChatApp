package com.example.project_app;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Patient {
    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private String numeroTelephone;
    private String description;
    private String imageUrl;
    private DatabaseReference cartReference;

    // Constructeur par défaut
    public Patient() {
        // Initialiser la référence au panier du patient
        this.cartReference = FirebaseDatabase.getInstance().getReference().child("patient_carts");
    }
    // Constructeur avec tous les champs
    public Patient(String nom, String prenom, int age, String adresse, String numeroTelephone, String description) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
        this.description = description;

    }

    // Getters et setters pour chaque champ

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}