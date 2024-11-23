
package com.example.project_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class DoctorProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DoctorProductListAdapter adapter;
    private ArrayList<Product> productList;
    private DatabaseReference doctorCartReference;
    private double doctorTotalPrice = 0.0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_doctor);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 items per row

        doctorCartReference = FirebaseDatabase.getInstance().getReference().child("doctor_cart");

        productList = new ArrayList<>();
        adapter = new DoctorProductListAdapter(productList, new DoctorProductListAdapter.OnDoctorProductInteractionListener() {
            @Override
            public void onDoctorAddToCart(Product product) {
                addToCart(product);
            }

            @Override
            public void onDoctorBuyNow(Product product) {
                buyNow(product);
            }
        });
        recyclerView.setAdapter(adapter);

        // Récupérer la catégorie des produits à afficher à partir des extras de l'intent
        String category = getIntent().getStringExtra("category");
        if (category != null) {
            fetchDoctorProducts(category);
        }

        ImageView imgCart = findViewById(R.id.imageViewdoctor);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorProductListActivity.this, DoctorCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchDoctorProducts(String category) {
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
        DoctorCartItem doctorCartItem = new DoctorCartItem(product, 1); // Add the product with quantity of 1
        String cartItemId = doctorCartReference.push().getKey(); // Generate a unique key for the new cart item
        if (cartItemId != null) {
            doctorCartReference.child(cartItemId).setValue(doctorCartItem); // Add the cart item to Firebase
            Toast.makeText(this, "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show(); // Toast when added to cart
        } else {
            Toast.makeText(this, "Error adding item to cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void buyNow(Product product) {
        doctorTotalPrice += product.getPrice();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            sendDoctorSms("0622495248", "Le montant total de vos achats est: " + doctorTotalPrice + " DH");
        }
    }

    private void sendDoctorSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
            Log.e("DoctorProductListActivity", "sendSms failed", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendDoctorSms("0622495248", "Le montant total de vos achats est: " + doctorTotalPrice + " DH");
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
