package com.example.project_app;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Dietpatient extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodDiaryAdapter dietAdapter;
    private List<FoodDiary> dietList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        recyclerView = findViewById(R.id.recyclerViewFoodDiary); // Utilisation de l'ID correct du RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dietList = new ArrayList<>();
        dietAdapter = new FoodDiaryAdapter(this, dietList);
        recyclerView.setAdapter(dietAdapter);

        // Récupérer le numéro de téléphone du patient depuis l'intent
        String patientTelephone = getIntent().getStringExtra("patientTelephone");

        // Appeler la méthode pour récupérer le régime alimentaire par numéro de téléphone
        getDietByPhoneNumber(patientTelephone);
    }

    private void getDietByPhoneNumber(String phoneNumber) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("food_diary");

        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dietList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodDiary diet = snapshot.getValue(FoodDiary.class);
                    if (diet != null) {
                        dietList.add(diet);
                    }
                }
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Dietpatient.this, "Erreur de base de données: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}