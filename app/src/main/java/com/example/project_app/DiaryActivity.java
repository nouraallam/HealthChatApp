package com.example.project_app;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.view.View;



public class DiaryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFoodDiary;
    private FoodDiaryAdapter foodDiaryAdapter;
    private List<FoodDiary> foodDiaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        recyclerViewFoodDiary = findViewById(R.id.recyclerViewFoodDiary);
        recyclerViewFoodDiary.setLayoutManager(new LinearLayoutManager(this));
        foodDiaryList = new ArrayList<>();
        foodDiaryAdapter = new FoodDiaryAdapter(this, foodDiaryList);
        recyclerViewFoodDiary.setAdapter(foodDiaryAdapter);

        // Query to get data from Firebase
        Query query = FirebaseDatabase.getInstance().getReference().child("food_diary");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodDiaryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodDiary foodDiary = snapshot.getValue(FoodDiary.class);
                    foodDiaryList.add(foodDiary);
                }
                foodDiaryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        // Ajouter l'écouteur de clic sur l'icône de l'image affiche ici
        foodDiaryAdapter.setOnItemClickListener(new FoodDiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Appeler le listener onItemClick de l'adaptateur
                if (foodDiaryAdapter != null && position != RecyclerView.NO_POSITION) {
                    foodDiaryAdapter.onItemClick(position);
                }
            }
        });



    }
}