package com.example.project_app;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;

public class ViewCalendarActivity extends AppCompatActivity {
    private static final String TAG = "ViewCalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar);

        // Trouver le CalendarView dans le layout
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Ajouter un listener pour écouter les changements de date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Vous pouvez ajouter ici toute action que vous souhaitez effectuer lorsqu'une date est sélectionnée
        });
    }
}
