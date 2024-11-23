package com.example.project_app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private DatabaseReference db;
    private Map<String, StringBuilder> remarksMap;
    private List<Remark> remarksList;
    private ListView remarksListView;
    private List<String> daysWithRemarks;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        db = FirebaseDatabase.getInstance().getReference();
        remarksMap = new HashMap<>();
        remarksList = new ArrayList<>();
        daysWithRemarks = new ArrayList<>();
        calendar = Calendar.getInstance();

        // Charger toutes les remarques depuis Firebase lors de la création de l'activité
        loadAllRemarks();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                loadRemarks(year, month, dayOfMonth);
                showAddRemarkDialog(year, month, dayOfMonth);
            }
        });

        remarksListView = findViewById(R.id.remarksListView);
        RemarksAdapter adapter = new RemarksAdapter(this, remarksList);
        remarksListView.setAdapter(adapter);

        // Appeler la méthode pour marquer les dates avec des remarques
        markDatesWithRemarks();
    }


    private void showAddRemarkDialog(int year, int month, int dayOfMonth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une remarque");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String remark = input.getText().toString();
            if (!remark.isEmpty()) {
                Log.d(TAG, "Adding remark: " + remark);
                saveRemark(year, month, dayOfMonth, remark);
            } else {
                Toast.makeText(this, "Remarque vide!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveRemark(int year, int month, int dayOfMonth, String remark) {
        Log.d(TAG, "Saving remark for date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
        Remark note = new Remark(year, month, dayOfMonth, remark);

        String key = db.child("remarks").push().getKey();
        if (key != null) {
            db.child("remarks").child(key).setValue(note)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Remark added with key: " + key);
                        Toast.makeText(CalendarActivity.this, "Remarque ajoutée", Toast.LENGTH_SHORT).show();
                        // Mettre à jour le texte du jour sélectionné dans le CalendarView
                        CalendarView calendarView = findViewById(R.id.calendarView);
                        calendarView.setDate(calendarView.getDate());
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding remark", e);
                        Toast.makeText(CalendarActivity.this, "Erreur lors de l'ajout de la remarque", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e(TAG, "Error getting database key");
            Toast.makeText(CalendarActivity.this, "Erreur lors de l'ajout de la remarque", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRemarks(int year, int month, int dayOfMonth) {
        String dateKey = year + "-" + (month + 1) + "-" + dayOfMonth;
        StringBuilder remarks = remarksMap.get(dateKey);

        // Mettre à jour la liste des remarques
        RemarksAdapter adapter = (RemarksAdapter) remarksListView.getAdapter();
        adapter.clear();
        if (remarks != null && remarks.length() > 0) {
            String[] remarkArray = remarks.toString().split("\n");
            for (String remark : remarkArray) {
                Remark note = new Remark(year, month, dayOfMonth, remark);
                adapter.add(note);
            }
        }

        // Mettre à jour la date actuelle avec la date sélectionnée
        calendar.set(year, month, dayOfMonth);

        // Vérifier si le jour a une remarque et le mettre en surbrillance si nécessaire
        CalendarView calendarView = findViewById(R.id.calendarView);
        if (daysWithRemarks.contains(dateKey)) {
            calendarView.setDate(calendar.getTimeInMillis(), true, true);
        } else {
            calendarView.setDate(calendar.getTimeInMillis(), false, true);
        }
    }

    private void loadAllRemarks() {
        db.child("remarks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Remark note = snapshot.getValue(Remark.class);
                        if (note != null) {
                            String dateKey = note.getYear() + "-" + (note.getMonth() + 1) + "-" + note.getDayOfMonth();
                            StringBuilder remarks = remarksMap.get(dateKey);
                            if (remarks == null) {
                                remarks = new StringBuilder();
                            }
                            remarks.append(note.getRemark()).append("\n");
                            remarksMap.put(dateKey, remarks);
                            remarksList.add(note);

                            // Ajouter le jour à la liste des jours avec des remarques
                            daysWithRemarks.add(dateKey);
                        }
                    }
                } else {
                    Log.d(TAG, "No remarks found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error loading remarks", databaseError.toException());
                Toast.makeText(CalendarActivity.this, "Erreur lors du chargement des remarques", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void markDatesWithRemarks() {
        // Couleur de fond spécifique pour les jours avec des remarques
        int backgroundColor = getResources().getColor(R.color.remark_day_background);

        // Parcourir la liste des dates avec des remarques
        for (String date : daysWithRemarks) {
            // Convertir la date en objets year, month, day
            String[] dateParts = date.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // Soustraire 1 car le mois commence à 0
            int dayOfMonth = Integer.parseInt(dateParts[2]);

            // Rechercher la vue de cette date dans le CalendarView
            long dateInMillis = calendar.getTimeInMillis();
            calendar.set(year, month, dayOfMonth);
            long targetDateInMillis = calendar.getTimeInMillis();
            CalendarView calendarView = findViewById(R.id.calendarView);
            if (dateInMillis == targetDateInMillis) {
                calendarView.setDate(targetDateInMillis, true, true);
                break;
            }
        }
    }
}

