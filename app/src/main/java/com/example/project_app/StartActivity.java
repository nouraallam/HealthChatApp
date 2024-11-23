package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.content.Intent;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Récupérer l'image du médecin
        ImageView doctorImageView = findViewById(R.id.doctorImageView);

        // Ajouter un écouteur de clic à l'image du médecin
        doctorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour démarrer ProfileDoctorActivity
                Intent intent = new Intent(StartActivity.this, ProfileDoctorActivity.class);
                startActivity(intent); // Démarrer l'activité
            }
        });

        // Configurer les écouteurs de clic pour les icônes de la barre de navigation inférieure
        ImageView homeIcon = findViewById(R.id.imageView_explorer);
        ImageView cartIcon = findViewById(R.id.imageView_my_order);
        ImageView profileIcon = findViewById(R.id.imageView_profile);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers StartActivity
                Intent intent = new Intent(StartActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers CategoryShow
                Intent intent = new Intent(StartActivity.this, CateroryShow.class);
                startActivity(intent);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers ProfileDoctorActivity
                Intent intent = new Intent(StartActivity.this, ProfileDoctorActivity.class);
                startActivity(intent);
            }
        });


        ImageButton option7Button = findViewById(R.id.option7Button);

        option7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité du calendrier
                Intent intent = new Intent(StartActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    // Méthode appelée lorsque l'icône "Chat" est cliquée
    public void onOption2Click(View view) {
        // Créer une intention pour démarrer ChatActivity
        Intent intent = new Intent(this, CategoryShowDoctor.class);
        startActivity(intent); // Démarrer l'activité
    }


    // Méthode appelée lorsque l'icône du patient est cliquée
    public void onPatientIconClick(View view) {
        // Créer une intention pour démarrer PatientFormActivity
        Intent intent = new Intent(this, PatientFormActivity.class);
        startActivity(intent); // Démarrer l'activité
    }
    public void onPatientIconClicked(View view) {
        // Créer une intention pour démarrer PatientListActivity
        Intent intent = new Intent(this, PatientListActivity.class);
        startActivity(intent); // Démarrer l'activité
    }
    public void onOption3Click(View view) {
        // Créer une intention pour démarrer ChatActivity
        Intent intent = new Intent(this, FoodDiaryActivity.class);
        startActivity(intent); // Démarrer l'activité
    }
    public void onOption4Click(View view) {
        // Créer une intention pour démarrer ChatActivity
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent); // Démarrer l'activité
    }

    public void onOption5Click(View view) {
        // Créer une intention pour démarrer ChatActivity
        Intent intent = new Intent(this, ProductSaveActivity.class);
        startActivity(intent); // Démarrer l'activité
    }
    public void onOption8Click(View view) {
        Intent intent = new Intent(this, Activitiregl.class);
        startActivity(intent);
    }
}
