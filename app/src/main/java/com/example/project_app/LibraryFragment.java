package com.example.project_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LibraryFragment extends Fragment {
    // Implémentez le constructeur vide requis
    public LibraryFragment() {
        // Requis pour le Fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Trouvez les TextViews dans votre layout fragment_library.xml et définissez le texte
        TextView appointmentAvailabilityTextView = view.findViewById(R.id.appointmentAvailabilityTextView);
        TextView cancellationPolicyTextView = view.findViewById(R.id.cancellationPolicyTextView);
        TextView appointmentConfirmationTextView = view.findViewById(R.id.appointmentConfirmationTextView);

        appointmentAvailabilityTextView.setText("Disponibilité des Rendez-vous :\nLes rendez-vous peuvent être pris en ligne ou par téléphone. Consultez l'agenda pour les créneaux disponibles.");
        cancellationPolicyTextView.setText("Politique d'Annulation et de Report :\nAnnulez ou reportez votre rendez-vous au moins 24 heures à l'avance pour éviter des frais d'annulation.");
        appointmentConfirmationTextView.setText("Confirmation de Rendez-vous :\nVous recevrez une confirmation de rendez-vous par e-mail et un rappel automatique la veille du rendez-vous.");

        return view;
    }
}
