package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Méthode appelée lorsque le texte "Start" est cliqué
    public void startActivityMethod(View view) {
        // Créer une intention pour démarrer StartActivity
        Intent intent = new Intent(this, ChoicActivity.class);
        startActivity(intent); // Démarrer l'activité
    }
    // Méthode appelée lorsque le texte "Already have an account? Sign in" est cliqué

    public void startRegistrationActivity(View view) {
        // Créer une intention pour démarrer LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent); // Démarrer l'activité
    }

}
