package com.example.project_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText usernameEditText, emailEditText;
    private Button updateButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_doctor);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        usernameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.address);

        updateButton = findViewById(R.id.update);

        // Get current user
        FirebaseUser user = mAuth.getCurrentUser();

        // Set current user's data to EditText fields
        if (user != null) {
            usernameEditText.setText(user.getDisplayName());
            emailEditText.setText(user.getEmail());
        }

        // Update button click listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        String userId = mAuth.getCurrentUser().getUid();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        // Update data in Firebase Database
        Doctor doctor = new Doctor(userId, username, email);
        databaseReference.child(userId).setValue(doctor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
