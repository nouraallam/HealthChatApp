package com.example.project_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textview.MaterialTextView;

public class profil_docterpty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_docterpt);

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

        // Trouver l'icône de retour
        ImageView backButton = findViewById(R.id.back_button);

        // Ajouter un écouteur d'événements sur l'icône de retour
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers StartActivity
                Intent intent = new Intent(profil_docterpty.this, HomeActivity.class);
                startActivity(intent);
                // Optionnel : Si vous souhaitez terminer l'activité actuelle
                finish();
            }
        });
    }
}
