package com.example.project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_app.FoodDiary;
import java.util.List;
import com.example.project_app.R;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class FoodDiaryAdapter extends RecyclerView.Adapter<FoodDiaryAdapter.FoodDiaryViewHolder> {

    private Context context;
    private List<FoodDiary> foodDiaryList;
    private OnItemClickListener listener;

    public FoodDiaryAdapter(Context context, List<FoodDiary> foodDiaryList) {
        this.context = context;
        this.foodDiaryList = foodDiaryList;
    }

    @NonNull
    @Override
    public FoodDiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_diary_item, parent, false);
        return new FoodDiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDiaryViewHolder holder, int position) {
        FoodDiary foodDiary = foodDiaryList.get(position);
        holder.textViewFullName.setText(foodDiary.getfullName());
        holder.textViewDate.setText(foodDiary.getDate());
        // Assurez-vous d'ajouter d'autres données à afficher ici

        // Écouteur de clic sur l'icône de l'image affiche
        holder.imageViewAffiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gérer le clic sur l'icône de l'image affiche ici
                // Vous pouvez accéder aux autres données de FoodDiary à partir de foodDiary
                // Par exemple, vous pouvez afficher les détails dans une boîte de dialogue ou démarrer une nouvelle activité
                // pour afficher les autres données du FoodDiary.
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    FoodDiary clickedItem = foodDiaryList.get(adapterPosition);
                    // Afficher les détails du FoodDiary dans une boîte de dialogue ou une nouvelle activité
                    showDialogWithDetails(clickedItem);
                }
            }
        });
    }

    // Méthode pour afficher les détails du FoodDiary
    private void showDialogWithDetails(FoodDiary foodDiary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Détails du Food Diary");

        // Construction du message avec les détails du FoodDiary
        StringBuilder message = new StringBuilder();
        message.append("Nom complet: ").append(foodDiary.getfullName()).append("\n");
        message.append("Date: ").append(foodDiary.getDate()).append("\n");
        message.append("Repas: ").append(foodDiary.getRepas()).append("\n");
        message.append("Aliments: ").append(foodDiary.getAliments()).append("\n");
        message.append("Quantité: ").append(foodDiary.getQuantite()).append("\n");
        message.append("Valeurs nutritionnelles: ").append(foodDiary.getValeursNutritionnelles()).append("\n");
        message.append("Note: ").append(foodDiary.getNote()).append("\n");

        builder.setMessage(message.toString());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }





    @Override
    public int getItemCount() {
        return foodDiaryList.size();
    }

    public class FoodDiaryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName;
        TextView textViewDate;
        ImageView imageViewAffiche;
        // Ajoutez d'autres TextView pour les autres données

        public FoodDiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            imageViewAffiche = itemView.findViewById(R.id.imageViewAffiche);
            // Initialisez d'autres TextView ici
        }
    }
    // Définition de la méthode onItemClick
    public void onItemClick(int position) {
        if (listener != null) {
            listener.onItemClick(position);
        }
    }
    // Interface pour gérer les clics sur les éléments de l'adapter
    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    // Méthode pour définir l'écouteur de clic externe
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}