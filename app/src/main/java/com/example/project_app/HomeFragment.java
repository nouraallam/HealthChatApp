package com.example.project_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    // Implémentez le constructeur vide requis
    public HomeFragment() {
        // Requis pour le Fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Trouvez les TextViews dans votre layout fragment_home.xml et définissez le texte
        TextView nutritionalAdviceTextView = view.findViewById(R.id.nutritionalAdviceTextView);
        TextView dietaryRestrictionsTextView = view.findViewById(R.id.dietaryRestrictionsTextView);
        TextView standardsRegulationsTextView = view.findViewById(R.id.standardsRegulationsTextView);

        nutritionalAdviceTextView.setText("Conseils Nutritionnels :\nUne alimentation équilibrée est essentielle pour maintenir votre santé. Consommez des fruits, des légumes et des protéines maigres.");
        dietaryRestrictionsTextView.setText("Restrictions Alimentaires :\nÉvitez les aliments riches en gras saturés et en sucres ajoutés pour prévenir les maladies cardiovasculaires et le diabète.");
        standardsRegulationsTextView.setText("Normes et Règlements :\nRespectez les normes alimentaires locales et les recommandations des autorités de santé pour garantir la qualité et la sécurité des aliments.");

        return view;
    }
}
