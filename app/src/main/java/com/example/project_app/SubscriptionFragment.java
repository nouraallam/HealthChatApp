package com.example.project_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubscriptionFragment extends Fragment {

    public SubscriptionFragment() {
        // Constructeur vide requis
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        // Trouvez les TextViews dans votre layout fragment_subscription.xml
        TextView confidentialityTextView = view.findViewById(R.id.confidentialityTextView);
        TextView availabilityTextView = view.findViewById(R.id.availabilityTextView);
        TextView questionTypesTextView = view.findViewById(R.id.questionTypesTextView);
        TextView behaviorTextView = view.findViewById(R.id.behaviorTextView);
        TextView usageInstructionsTextView = view.findViewById(R.id.usageInstructionsTextView);
        TextView moderationPolicyTextView = view.findViewById(R.id.moderationPolicyTextView);

        // Définissez le texte pour chaque TextView avec les informations de votre chapitre
        confidentialityTextView.setText("Les conversations dans le chat sont strictement confidentielles. Ne partagez pas d'informations personnelles sensibles.");
        availabilityTextView.setText("Les médecins sont disponibles pour le chat du lundi au vendredi, de 9h à 17h. Les week-ends sont réservés aux urgences médicales uniquement.");
        questionTypesTextView.setText("Vous pouvez poser des questions médicales générales, demander des conseils sur vos traitements actuels, ou clarifier des informations sur des médicaments.");
        behaviorTextView.setText("Soyez respectueux envers les médecins et les autres utilisateurs. Évitez les langages offensants ou agressifs.");
        usageInstructionsTextView.setText("Pour démarrer une conversation, cliquez sur l'icône de chat dans le coin inférieur droit. Tapez votre message et appuyez sur Envoyer.");
        moderationPolicyTextView.setText("Les messages violant les règles de comportement seront supprimés. En cas de comportement répété inapproprié, l'accès au chat peut être restreint.");

        return view;
    }
}
