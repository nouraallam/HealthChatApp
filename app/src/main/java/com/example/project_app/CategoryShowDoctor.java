package com.example.project_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;
import android.content.Intent;
import android.widget.ImageView;
import android.view.View;

public class CategoryShowDoctor extends AppCompatActivity {

    private String patientTelephone; // Numéro de téléphone du patient
    private int[] mImageIds = {
            R.drawable.card1, R.drawable.card2, R.drawable.card3,
            R.drawable.card4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);

        patientTelephone = getIntent().getStringExtra("patientTelephone");


        // Initialize RecyclerView for categories
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set Adapter for Category RecyclerView
        recyclerView.setAdapter(new CategoryAdapter(this));

        // Initialize RecyclerView for recently added items
        RecyclerView recentlyAddedRecyclerView = findViewById(R.id.recentlyAddedRecyclerView);
        recentlyAddedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // Set Adapter for recently added items RecyclerView
        recentlyAddedRecyclerView.setAdapter(new RecentlyAddedAdapter(this, getRecentlyAddedItems(), mImageIds));

        ImageView allCategoryImage = findViewById(R.id.allCategoryImage);

        // Ajouter un écouteur de clic à l'ImageView
        allCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité ShopActivity
                Intent intent = new Intent(CategoryShowDoctor.this, ShopDoctor.class);
                intent.putExtra("patientTelephone", patientTelephone); // Passage du numéro de téléphone
                startActivity(intent);
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
                Intent intent = new Intent(CategoryShowDoctor.this, StartActivity.class);
                startActivity(intent);
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers CategoryShow
                Intent intent = new Intent(CategoryShowDoctor.this, ShopDoctor.class);
                startActivity(intent);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers ProfileDoctorActivity
                Intent intent = new Intent(CategoryShowDoctor.this, ProfileDoctorActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<Product> getRecentlyAddedItems() {
        // Replace with your logic to get recently added items
        return Arrays.asList(
                new Product("Kiwi", "Kiwi has high water content and also provide some fiber", 20.0, "card2", "Category 1", "Kg", 1),
                new Product("strawberry", "strawberry has high water content and also provide some fiber", 20.0, "Card1", "Category 2", "Kg", 2),
                new Product("Papaya", "Papaya has high water content and also provide some fiber", 30.0, "Card3", "Category 3", "Kg", 3),
                new Product("Watermelon", "Watermelon", 20.0, "Card4", "Category 4", "Kg", 4)
        );
    }
}
