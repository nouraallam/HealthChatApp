package com.example.project_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoicActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chioce);

        Button patientButton = findViewById(R.id.patientButton);
        Button doctorButton = findViewById(R.id.doctorButton);

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez ici le code que vous souhaitez exécuter lorsque le bouton Patient est cliqué
                // Redirection vers l'activité StartActivity lorsque le bouton Doctor est cliqué
                Intent intent = new Intent(ChoicActivity .this, login.class);
                startActivity(intent);
            }
        });

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'activité StartActivity lorsque le bouton Doctor est cliqué
                Intent intent = new Intent(ChoicActivity .this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
