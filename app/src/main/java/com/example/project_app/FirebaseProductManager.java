package com.example.project_app;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseProductManager {
    private DatabaseReference databaseReference;

    public FirebaseProductManager() {
        // Initialisez votre référence à la base de données Firebase ici
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
    }

    public void addProduct(Product product) {
        String productId = databaseReference.push().getKey();
        // Assurez-vous d'affecter également l'URL de l'image et la catégorie au produit
        String imageUrl = product.getImageUrl(); // Récupérez l'URL de l'image depuis le produit

        // Enregistrez le produit dans la base de données avec l'URL de l'image et la catégorie
        databaseReference.child(productId).setValue(product);

        // Ajoutez également l'URL de l'image
        databaseReference.child(productId).child("imageUrl").setValue(imageUrl);
    }

    // Ajoutez les méthodes pour supprimer, mettre à jour et récupérer les produits depuis Firebase
}
