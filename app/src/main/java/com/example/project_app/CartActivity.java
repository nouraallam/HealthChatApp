package com.example.project_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private CartListAdapter cartAdapter;
    private ArrayList<CartItem> cartItemList;
    private DatabaseReference cartReference;
    private TextView tvTotalPrice;
    private String patientTelephone;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        // Récupérer le numéro de téléphone du patient à partir de l'intent
        patientTelephone = getIntent().getStringExtra("patientTelephone");

        if (patientTelephone != null) {
            // Enregistrer le numéro de téléphone dans les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("patientTelephone", patientTelephone);
            editor.apply();
        } else {
            // Récupérer le numéro de téléphone du patient depuis les préférences partagées
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            patientTelephone = sharedPreferences.getString("patientTelephone", null);
        }

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new GridLayoutManager(this, 2));

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnBuyNow = findViewById(R.id.btnBuyNow);

        cartReference = FirebaseDatabase.getInstance().getReference().child("cart");

        cartItemList = new ArrayList<>();
        cartAdapter = new CartListAdapter(cartItemList);
        recyclerViewCart.setAdapter(cartAdapter);

        fetchCartItems();

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CartActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CartActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    sendSms(patientTelephone, "Le montant total de vos achats est: " + tvTotalPrice.getText().toString());
                }
            }
        });
    }

    private void fetchCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String patientTelephone = sharedPreferences.getString("patientTelephone", null);

        if (patientTelephone == null) {
            Toast.makeText(this, "Erreur : Patient non authentifié", Toast.LENGTH_SHORT).show();
            return;
        }

        cartReference.child(patientTelephone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItemList.clear();
                double totalPrice = 0.0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = snapshot.getValue(CartItem.class);
                    if (cartItem != null) {
                        cartItemList.add(cartItem);
                        totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
                    }
                }
                tvTotalPrice.setText("Total: " + totalPrice + " DH");
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CartActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            Log.e("CartActivity", "sendSms failed", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms(patientTelephone, "Le montant total de vos achats est: " + tvTotalPrice.getText().toString());
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
