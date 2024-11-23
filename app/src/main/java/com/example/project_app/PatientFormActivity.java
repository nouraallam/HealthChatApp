package com.example.project_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class PatientFormActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final String TAG = "PatientFormActivity";

    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private String numeroTelephone;
    private String description;
    private String imageUrl; // Variable pour stocker l'URL de l'image

    private Button selectImageButton; // Bouton de sélection d'image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        // Initialiser la référence de la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialiser la référence du stockage Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Récupérer le bouton de sauvegarde
        Button saveButton = findViewById(R.id.saveButton);

        // Récupérer le bouton de sélection d'image
        selectImageButton = findViewById(R.id.selectImageButton);

        // Écouter le clic sur le bouton de sélection d'image
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // Écouter le clic sur le bouton de sauvegarde
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si une image a été sélectionnée
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Sauvegarder les données du patient dans Firebase
                    savePatientToFirebase();
                } else {
                    // Afficher un message si aucune image n'a été sélectionnée
                    Toast.makeText(PatientFormActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Méthode pour sélectionner une image depuis la galerie
    public void selectImage() {
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
        String imageName = "patient_" + System.currentTimeMillis() + ".jpg";

        // Référence pour stocker l'image dans Firebase Storage
        StorageReference imageRef = mStorageRef.child("images").child(imageName);

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
                        Toast.makeText(PatientFormActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void savePatientToFirebase() {
        // Récupérer les valeurs des champs du formulaire
        EditText nomEditText = findViewById(R.id.editTextNom);
        EditText prenomEditText = findViewById(R.id.editTextPrenom);
        EditText ageEditText = findViewById(R.id.editTextAge);
        EditText adresseEditText = findViewById(R.id.editTextAdresse);
        EditText numeroTelephoneEditText = findViewById(R.id.editTextNumeroTelephone);
        EditText descriptionEditText = findViewById(R.id.editTextDescription);

        nom = nomEditText.getText().toString().trim();
        prenom = prenomEditText.getText().toString().trim();
        String ageText = ageEditText.getText().toString().trim();
        if (!ageText.isEmpty()) {
            age = Integer.parseInt(ageText);
        } else {
            Toast.makeText(PatientFormActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
            return;
        }
        adresse = adresseEditText.getText().toString().trim();
        numeroTelephone = numeroTelephoneEditText.getText().toString().trim();
        description = descriptionEditText.getText().toString().trim();

        // Générer une clé unique pour le patient
        String patientId = mDatabase.child("patients").push().getKey();

        // Créer un objet Patient avec les données
        Patient patient = new Patient(nom, prenom, age, adresse, numeroTelephone, description);
        patient.setImageUrl(imageUrl);

        // Enregistrer le patient dans la base de données Firebase Realtime
        mDatabase.child("patients").child(patientId).setValue(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Enregistrement réussi
                        Toast.makeText(PatientFormActivity.this, "Patient enregistré avec succès", Toast.LENGTH_SHORT).show();
                        finish(); // Fermer l'activité du formulaire du patient après l'enregistrement
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Enregistrement échoué
                        Toast.makeText(PatientFormActivity.this, "Échec de l'enregistrement du patient", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
