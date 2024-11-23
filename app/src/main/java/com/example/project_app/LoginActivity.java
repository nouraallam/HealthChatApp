package com.example.project_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button loginButton;
    private TextView signUpTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageFilterView imageFilterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialisation des vues
        editTextEmail = findViewById(R.id.editTexLogEmail);
        editTextPassword = findViewById(R.id.editTextLogPassword);
        loginButton = findViewById(R.id.logbutton);
        signUpTextView = findViewById(R.id.logsignup);
        imageFilterView = findViewById(R.id.imageFilterView4);

        // Définition des listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel à une méthode pour gérer l'authentification
                performLogin();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'activité d'inscription
                redirectToSignUpActivity();
            }
        });
        imageFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToChoicActivityDoc();
            }
        });
    }

    // Méthode pour gérer l'authentification
    private void performLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Veuillez saisir votre email et votre mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Connexion de l'utilisateur avec Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Connexion réussie
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                // Récupérer les informations de l'utilisateur à partir de Firebase Realtime Database
                                mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Doctor userData = dataSnapshot.getValue(Doctor.class);
                                            if (userData != null) {
                                                String userEmail = userData.getEmail();
                                                Toast.makeText(LoginActivity.this, "Authentification réussie pour l'email : " + userEmail, Toast.LENGTH_SHORT).show();
                                                redirectToStartActivity();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Gestion des erreurs
                                        Toast.makeText(LoginActivity.this, "Erreur lors de la récupération des informations de l'utilisateur", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            // Échec de la connexion
                            Toast.makeText(LoginActivity.this, "Échec de l'authentification. Veuillez vérifier vos identifiants.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Méthode pour rediriger vers l'activité d'inscription
    private void redirectToSignUpActivity() {
        // Intent pour démarrer l'activité InscriActivity
        Intent signUpIntent = new Intent(LoginActivity.this, InscriActivity.class);
        startActivity(signUpIntent);
    }

    // Méthode pour rediriger vers StartActivity après une authentification réussie
    private void redirectToStartActivity() {
        Intent startIntent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(startIntent);
        // Terminez LoginActivity pour éviter de revenir en arrière
        finish();
    }
    private void redirectToChoicActivityDoc() {
        Intent choicIntent = new Intent(LoginActivity.this, ChoicActivity.class);
        startActivity(choicIntent);
    }
}
