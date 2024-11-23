package com.example.project_app;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

public class UserListActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_doctor_item);

        // Initialiser la référence de la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Récupérer la référence de RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser l'adaptateur avec une liste vide et une implémentation de OnItemClickListener
        mAdapter = new UserAdapter(new ArrayList<>(), new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String username) {
                // Gérer l'action lorsque l'utilisateur clique sur un nom d'utilisateur
                // Par exemple, ouvrir un profil utilisateur, démarrer une conversation, etc.
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        // Charger les utilisateurs depuis Firebase
        loadUsersFromFirebase();
    }

    private void loadUsersFromFirebase() {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> usernames = new ArrayList<>(); // Liste pour stocker les noms d'utilisateur uniquement
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String username = snapshot.child("username").getValue(String.class);
                    if (username != null) {
                        usernames.add(username); // Ajoutez le nom d'utilisateur à la liste
                    }
                }
                // Mettre à jour l'adaptateur avec la liste des noms d'utilisateur
                mAdapter.setUserList(usernames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs
                Toast.makeText(UserListActivity.this, "Erreur : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
