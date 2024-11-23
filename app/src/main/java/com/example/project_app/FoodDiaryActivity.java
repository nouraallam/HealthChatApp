package com.example.project_app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class FoodDiaryActivity extends AppCompatActivity {
    private EditText editTextFullName;
    private EditText editTextDate;
    private EditText editTextPhoneNumber;
    private EditText editTextRepas;
    private EditText editTextAliments;
    private EditText editTextQuantite;
    private EditText editTextValeursNutritionnelles;
    private EditText editTextNote;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        // Référence à la base de données Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("food_diary");

        // Initialisation des EditText
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextRepas = findViewById(R.id.editTextRepas);
        editTextAliments = findViewById(R.id.editTextAliments);
        editTextQuantite = findViewById(R.id.editTextQuantite);
        editTextValeursNutritionnelles = findViewById(R.id.editTextValeursNutritionnelles);
        editTextNote = findViewById(R.id.editTextNote);

        // Ajout d'un écouteur sur le bouton "Save"
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFoodDiary();
            }
        });

        // Ajouter un OnClickListener à l'EditText de la date
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        // Obtenir la date actuelle
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer un DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Mettre à jour l'EditText de la date avec la date sélectionnée
                        editTextDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);

        // Afficher le DatePickerDialog
        datePickerDialog.show();
    }

    private void saveFoodDiary() {
        // Récupération des valeurs des champs
        String fullName = editTextFullName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String repas = editTextRepas.getText().toString().trim();
        String aliments = editTextAliments.getText().toString().trim();
        String quantite = editTextQuantite.getText().toString().trim();
        String valeursNutritionnelles = editTextValeursNutritionnelles.getText().toString().trim();
        String note = editTextNote.getText().toString().trim();

        // Vérification des champs non vides
        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(repas) && !TextUtils.isEmpty(aliments) &&
                !TextUtils.isEmpty(quantite) && !TextUtils.isEmpty(valeursNutritionnelles) && !TextUtils.isEmpty(note)) {

            // Création d'un nouvel objet FoodDiary
            FoodDiary foodDiary = new FoodDiary(fullName,phoneNumber, date, repas, aliments, quantite, valeursNutritionnelles, note);

            // Ajout des données à Firebase
            databaseReference.push().setValue(foodDiary);

            // Affichage d'un message de succès
            Toast.makeText(this, "Food diary saved successfully", Toast.LENGTH_SHORT).show();

            // Effacer les champs après enregistrement
            editTextFullName.setText("");
            editTextDate.setText("");
            editTextPhoneNumber.setText("");
            editTextRepas.setText("");
            editTextAliments.setText("");
            editTextQuantite.setText("");
            editTextValeursNutritionnelles.setText("");
            editTextNote.setText("");
        } else {
            // Affichage d'un message d'erreur si un champ est vide
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}