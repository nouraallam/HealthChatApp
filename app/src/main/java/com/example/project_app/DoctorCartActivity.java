package com.example.project_app;

import android.Manifest;
import android.content.pm.PackageManager;
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

public class DoctorCartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private DoctorCartListAdapter cartAdapter;
    private ArrayList<DoctorCartItem> cartItemList;
    private DatabaseReference cartReference;
    private TextView tvTotalPrice;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 123;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_doctor);

        recyclerViewCart = findViewById(R.id.recyclerViewCartdoctor);
        recyclerViewCart.setLayoutManager(new GridLayoutManager(this, 2));

        tvTotalPrice = findViewById(R.id.tvTotalPricedoctor);

        cartReference = FirebaseDatabase.getInstance().getReference().child("doctor_cart");

        cartItemList = new ArrayList<>();
        cartAdapter = new DoctorCartListAdapter(cartItemList);
        recyclerViewCart.setAdapter(cartAdapter);

        fetchCartItemsdoctor();
        // Ecouteur d'événements pour le bouton "Buy Now"
        Button btnBuyNowDoctor = findViewById(R.id.btnBuyNowDoctor);
        btnBuyNowDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier les autorisations pour envoyer des SMS
                if (ContextCompat.checkSelfPermission(DoctorCartActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DoctorCartActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    // Envoyer le SMS avec les informations sur le montant total
                    sendDoctorSms("0622495248", "Le montant total de vos achats est: " + calculateTotalPrice() + " DH");
                }
            }
        });
    }

    private void fetchCartItemsdoctor() {
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                double totalPrice = 0.0;
                for (DataSnapshot doctorCartSnapshot : dataSnapshot.getChildren()) {
                    DoctorCartItem cartItem = doctorCartSnapshot.getValue(DoctorCartItem.class);
                    if (cartItem != null) {
                        cartItemList.add(cartItem);
                        totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
                    }
                }
                tvTotalPrice.setText("Total: " + totalPrice + " DH");
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DoctorCartActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Méthode pour calculer le montant total des achats dans le panier
    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (DoctorCartItem cartItem : cartItemList) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    // Méthode pour envoyer un SMS
    private void sendDoctorSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            Log.e("DoctorCartActivity", "sendSms failed", e);
        }
    }

    // Gestionnaire de résultats de la demande d'autorisation pour envoyer des SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Envoyer le SMS une fois les autorisations accordées
                sendDoctorSms("0622495248", "Le montant total de vos achats est: " + calculateTotalPrice() + " DH");
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
