package com.example.project_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private EditText editTextNom, editTextTelephone;
    public Button buttonLogin;
    private DatabaseReference mDatabase;
    private ImageFilterView imageFilterView;

    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginp);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("patients");

        editTextNom = findViewById(R.id.editTextNom);
        editTextTelephone = findViewById(R.id.editTextTelephone);
        buttonLogin = findViewById(R.id.buttonLogin);
        imageFilterView = findViewById(R.id.imageFilterView3);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editTextNom.getText().toString().trim();
                String telephone = editTextTelephone.getText().toString().trim();

                Log.d(TAG, "Nom: " + nom);
                Log.d(TAG, "Telephone: " + telephone);

                if (!nom.isEmpty() && !telephone.isEmpty()) {
                    checkPatient(nom, telephone);
                } else {
                    Toast.makeText(login.this, "Veuillez saisir le nom et le numéro de téléphone", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToChoicActivity();
            }
        });
    }

    private void checkPatient(final String nom, final String telephone) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean patientFound = false;
                String recipientId = null;
                String patientTelephone = null; // Ajout du numéro de téléphone
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient patient = snapshot.getValue(Patient.class);
                    if (patient != null && patient.getNom().equals(nom) && patient.getNumeroTelephone().equals(telephone)) {
                        patientFound = true;
                        recipientId = snapshot.getKey();
                        patientTelephone = patient.getNumeroTelephone(); // Récupération du numéro de téléphone
                        break;
                    }
                }
                if (patientFound) {
                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, HomeActivity.class);
                    intent.putExtra("recipientId", recipientId);
                    intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                    // Check if the phone number matches in food_diary
                    checkFoodDiaryPhoneNumber(telephone);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(login.this, "Login failed: Patient not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(login.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkFoodDiaryPhoneNumber(final String telephone) {
        DatabaseReference foodDiaryRef = FirebaseDatabase.getInstance().getReference().child("food_diary");
        foodDiaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean phoneMatchFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodDiary foodDiary = snapshot.getValue(FoodDiary.class);
                    if (foodDiary != null && foodDiary.getPhoneNumber().equals(telephone)) {
                        phoneMatchFound = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(login.this, "Erreur de base de données: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToChoicActivity() {
        Intent choicIntent = new Intent(login.this, ChoicActivity.class);
        startActivity(choicIntent);
    }
}