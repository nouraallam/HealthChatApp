package com.example.project_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProductSaveActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private static final int REQUEST_IMAGE_PICK = 1;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product);

        // Initialiser la référence de la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialiser la référence du stockage Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Récupérer le bouton de sauvegarde
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs du formulaire
                EditText nameEditText = findViewById(R.id.uploadTopic);
                String name = nameEditText.getText().toString().trim();

                EditText categoryEditText = findViewById(R.id.uploadDesc);
                String category = categoryEditText.getText().toString().trim();

                EditText priceEditText = findViewById(R.id.uploadLang);
                String priceText = priceEditText.getText().toString().trim();
                double price = Double.parseDouble(priceText);

                EditText descriptionEditText = findViewById(R.id.uploadesc);
                String description = descriptionEditText.getText().toString().trim();

                // Vérifier si une image a été téléchargée
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Créer un nouvel objet Product avec les valeurs récupérées
                    Product product = new Product(name, description, price, imageUrl, category, "Kg", 60);


                    // Enregistrer le produit dans Firebase
                    saveProductToFirebase(product);
                } else {
                    // Si aucune image n'a été téléchargée, informer l'utilisateur
                    Toast.makeText(ProductSaveActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Méthode pour sélectionner une image depuis la galerie
    public void selectImage(View view) {
        // Ouvrir la galerie pour sélectionner une image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // Stocker l'image dans Firebase Storage
            uploadImageToFirebaseStorage(imageUri);
        }
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        // Générer un nom unique pour l'image
        String imageName = "product_" + System.currentTimeMillis() + ".jpg";

        // Référence pour stocker l'image dans Firebase Storage
        StorageReference imageRef = mStorageRef.child("images/" + imageName);

        // Télécharger l'image dans Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Récupérer l'URL de téléchargement de l'image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Mettre à jour l'URL de l'image
                                imageUrl = uri.toString();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Gérer l'échec du téléchargement de l'image
                        Toast.makeText(ProductSaveActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveProductToFirebase(Product product) {
        // Générer une clé unique pour le produit
        String productId = mDatabase.child("products").push().getKey();

        // Enregistrer le produit dans la base de données Firebase Realtime
        mDatabase.child("products").child(productId).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Enregistrement réussi
                        Toast.makeText(ProductSaveActivity.this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Fermer l'activité du formulaire après l'enregistrement
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Enregistrement échoué
                        Toast.makeText(ProductSaveActivity.this, "Failed to save product", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
