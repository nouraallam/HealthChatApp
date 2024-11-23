package com.example.project_app;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.view.View;
import android.telephony.SmsManager;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;
import android.widget.ImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;



public class ProductListActivity extends AppCompatActivity  implements ProductListAdapter.OnProductInteractionListener{

    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<Product> productList;
    private DatabaseReference cartReference;
    private double totalPrice = 0.0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 123;
    private String patientTelephone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        // Récupérer le numéro de téléphone du patient à partir de l'intent
            patientTelephone = getIntent().getStringExtra("patientTelephone");
        if (patientTelephone != null) {
            // Enregistrer le numéro de téléphone dans les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("patientTelephone", patientTelephone);
            editor.apply();
        }else {
            // Récupérer le numéro de téléphone du patient depuis les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            patientTelephone = sharedPreferences.getString("patientTelephone", null);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 items per row

        cartReference = FirebaseDatabase.getInstance().getReference().child("cart");

        productList = new ArrayList<>();
        adapter = new ProductListAdapter(productList, this);
        recyclerView.setAdapter(adapter);

        // Récupérer la catégorie des produits à afficher à partir des extras de l'intent
        String category = getIntent().getStringExtra("category");
        if (category != null) {
            fetchProducts(category);
        }

        ImageView imgCart = findViewById(R.id.imageView6);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                patientTelephone = getIntent().getStringExtra("patientTelephone");
                startActivity(intent);
            }
        });
// Trouver l'icône de retour
        ImageView backButton = findViewById(R.id.imgBack);

        // Ajouter un écouteur d'événements sur l'icône de retour
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers StartActivity
                Intent intent = new Intent(ProductListActivity.this, ShopActivity.class);
                startActivity(intent);
                // Optionnel : Si vous souhaitez terminer l'activité actuelle
                finish();
            }
        });
    }

    private void fetchProducts(String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear(); // Clear existing list before adding new products
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void addToCart(Product product) {
        // Récupérer le numéro de téléphone du patient à partir des préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String patientTelephone = sharedPreferences.getString("patientTelephone", null);

        if (patientTelephone != null) {
            CartItem cartItem = new CartItem(product, 1); // Add the product with quantity of 1
            String cartItemId = cartReference.push().getKey(); // Generate a unique key for the new cart item
            if (cartItemId != null) {
                cartReference.child(patientTelephone).child(cartItemId).setValue(cartItem);
            } else {
                Toast.makeText(this, "Error adding item to cart", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Patient phone number not found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAddToCart(Product product) {
        addToCart(product);
        totalPrice += product.getPrice();
        Toast.makeText(this, "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBuyNow(Product product) {
        totalPrice += product.getPrice();
        // Dans votre méthode onCreate() ou là où vous souhaitez vérifier la permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Si l'application a déjà la permission, envoyez le SMS
            sendSms(patientTelephone, "Le montant total de vos achats est: " + totalPrice + " DH");
        }
    }

    private void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            Log.e("ProductListActivity", "sendSms failed", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, envoyer le SMS
                sendSms(patientTelephone, "Le montant total de vos achats est: " + totalPrice + " DH");
            } else {
                // Permission refusée, afficher un message à l'utilisateur
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
