package com.example.project_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import android.content.SharedPreferences;



public class ShopDoctor  extends AppCompatActivity {

    private LottieAnimationView animationViewShopping;
    private CardView vegetablesCard;
    private CardView fruitsCard;
    private CardView dairyCard;
    private CardView drinkCard;
    private CardView bakeryCard;
    private CardView vitaminCard;
    private String patientTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_doctor);


        // Récupérer le numéro de téléphone du patient à partir de l'intent
        patientTelephone = getIntent().getStringExtra("patientTelephone");
        if (patientTelephone != null) {
            // Enregistrer le numéro de téléphone dans les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("patientTelephone", patientTelephone);
            editor.apply();
        }
        // Initialisation des vues
        animationViewShopping = findViewById(R.id.animationView_shopping);

        // Vous pouvez également contrôler l'animation Lottie
        animationViewShopping.playAnimation();

        vegetablesCard = findViewById(R.id.fashion_card);
        fruitsCard = findViewById(R.id.electronics_card);
        dairyCard = findViewById(R.id.mobiles_card);
        drinkCard = findViewById(R.id.laptop_card);
        bakeryCard = findViewById(R.id.games_card);
        vitaminCard = findViewById(R.id.sports_card);

        vegetablesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("vegtables");
            }
        });

        fruitsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("fruits");
            }
        });

        dairyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("dairy");
            }
        });

        drinkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("drink");
            }
        });

        bakeryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("boulang");
            }
        });

        vitaminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProductsByCategory("vitamin");
            }
        });
    }

    private void fetchProductsByCategory(final String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Product> productList = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }

                Intent doctorProductListIntent = new Intent(ShopDoctor .this, DoctorProductListActivity.class);
                doctorProductListIntent.putParcelableArrayListExtra("productList", productList);
                doctorProductListIntent.putExtra("category", category);
                startActivity(doctorProductListIntent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}

