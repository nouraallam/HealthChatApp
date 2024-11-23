package com.example.project_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShortsFragment extends Fragment {
    // Implémentez le constructeur vide requis
    public ShortsFragment() {
        // Requis pour le Fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shorts, container, false);

        // Trouvez les TextViews dans votre layout fragment_shorts.xml et définissez le texte
        TextView catalogueTextView = view.findViewById(R.id.catalogueTextView);
        TextView orderProcessTextView = view.findViewById(R.id.orderProcessTextView);
        TextView returnPolicyTextView = view.findViewById(R.id.returnPolicyTextView);

        catalogueTextView.setText("Catalogue de Produits :\nVous pouvez acheter des médicaments sur ordonnance, des fournitures médicales telles que des tensiomètres et des thermomètres, et des produits de santé généraux.");
        orderProcessTextView.setText("Processus de Commande :\nAjoutez des produits à votre panier en cliquant sur 'Ajouter au panier'. Finalisez votre commande et choisissez le paiement à la livraison.");
        returnPolicyTextView.setText("Politique de Retour :\nPour des raisons de sécurité et d'hygiène, les produits médicaux livrés ne peuvent pas être retournés. Vérifiez les produits à la livraison.");

        return view;
    }
}
