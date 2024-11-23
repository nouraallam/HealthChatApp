package com.example.project_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class HomeActivity extends AppCompatActivity {

    private ImageButton myDoctors, appointment, diet,shop,clend,rul;
    private String recipientId; // ID du destinataire
    private String patientTelephone; // Numéro de téléphone du patient
    private AppCompatButton signOutBtn; // Déclaration du bouton signOutBtn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_patient);

        // Initialisation des vues
        myDoctors = findViewById(R.id.myDoctors);
        appointment = findViewById(R.id.chat);
        diet = findViewById(R.id.diet);
        shop = findViewById(R.id.profileIcon);
        rul = findViewById(R.id.option2Icon);
        clend = findViewById(R.id.option1Icon);
        signOutBtn = findViewById(R.id.signOutBtn); // Initialisation du bouton signOutBtn

        // Récupération des données de l'intent
        recipientId = getIntent().getStringExtra("recipientId");
        patientTelephone = getIntent().getStringExtra("patientTelephone");

        // Définition des écouteurs d'événements
        myDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer profil_docterpty Activity
                Intent intent = new Intent(HomeActivity.this, profil_docterpty.class);
                startActivity(intent); // Démarrer l'activité
            }
        });

        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer Dietpatient Activity
                Intent intent = new Intent(HomeActivity.this, Dietpatient.class);
                intent.putExtra("recipientId", recipientId); // Passage de l'ID du destinataire
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent); // Démarrer l'activité
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer chatwithDoc Activity
                Intent intent = new Intent(HomeActivity.this, chatwithDoc.class);
                intent.putExtra("recipientId", recipientId); // Passage de l'ID du destinataire
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent); // Démarrer l'activité
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer chatwithDoc Activity
                Intent intent = new Intent(HomeActivity.this, CateroryShow.class);
                intent.putExtra("recipientId", recipientId); // Passage de l'ID du destinataire
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent); // Démarrer l'activité
            }
        });
        rul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer chatwithDoc Activity
                Intent intent = new Intent(HomeActivity.this, Activitiregl.class);
                intent.putExtra("recipientId", recipientId); // Passage de l'ID du destinataire
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent); // Démarrer l'activité
            }
        });

        clend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer chatwithDoc Activity
                Intent intent = new Intent(HomeActivity.this, ViewCalendarActivity.class);
                intent.putExtra("recipientId", recipientId); // Passage de l'ID du destinataire
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent); // Démarrer l'activité
            }
        });
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer ChoicActivity
                Intent intent = new Intent(HomeActivity.this, ChoicActivity.class);
                startActivity(intent); // Démarrer l'activité
            }
        });
    }
}