package com.example.project_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PatientViewHolder extends RecyclerView.ViewHolder {
    private TextView mNameTextView;
    private TextView mAgeTextView;
    private ImageView mPersonImageView;
    private PatientAdapter.OnItemClickListener mListener;
    private List<Patient> mPatients; // Déclaration de la liste des patients

    public PatientViewHolder(@NonNull View itemView, PatientAdapter.OnItemClickListener listener, List<Patient> patients) {
        super(itemView);
        mListener = listener;
        mPatients = patients; // Initialisation de la liste des patients
        mNameTextView = itemView.findViewById(R.id.nameTextView);
        mAgeTextView = itemView.findViewById(R.id.phoneTextView);
        mPersonImageView = itemView.findViewById(R.id.personImageView);


        // Dans le ViewHolder, changez l'argument de la méthode onItemClick de String à Patient
        mPersonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mListener != null) {
                    mListener.onItemClick(mPatients.get(position)); // Passer l'objet Patient
                }
            }
        });
    }


    public void bind(Patient patient) {
        mNameTextView.setText(patient.getNom() + " " + patient.getPrenom());
        mAgeTextView.setText(String.valueOf(patient.getAge()));

        // Charger l'image à partir de Firebase Storage et l'afficher dans ImageView
        String imageUrl = patient.getImageUrl(); // Assurez-vous d'avoir la méthode getImageUrl() dans votre modèle Patient
        Picasso.get().load(imageUrl).into(mPersonImageView);
    }
}