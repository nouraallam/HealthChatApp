package com.example.project_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView; // Import ajouté
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class ProfileDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        // Trouver l'icône d'édition
        ImageView editButton = findViewById(R.id.edit_button);

        // Ajouter un écouteur d'événements sur l'icône d'édition
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers EditProfileDoctorActivity
                Intent intent = new Intent(ProfileDoctorActivity.this, EditProfileDoctorActivity.class);
                startActivity(intent);
            }
        });
        // Trouver l'icône de retour
        ImageView backButton = findViewById(R.id.back_button);

        // Ajouter un écouteur d'événements sur l'icône de retour
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers StartActivity
                Intent intent = new Intent(ProfileDoctorActivity.this, StartActivity.class);
                startActivity(intent);
                // Optionnel : Si vous souhaitez terminer l'activité actuelle
                finish();
            }
        });
        MaterialTextView doctorAboutTextView = findViewById(R.id.doctor_about);
        doctorAboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL du lien que vous souhaitez ouvrir
                String url = "https://web.facebook.com/nutripromaroc/?_rdc=1&_rdr";

                // Création de l'intention pour ouvrir le lien dans un navigateur Web
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
