package com.example.project_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<Patient> mPatients;
    private OnItemClickListener mListener;

    public PatientAdapter(List<Patient> patients, OnItemClickListener listener) {
        mPatients = patients;
        mListener = listener;
    }

    public void setPatients(List<Patient> patients) {
        mPatients = patients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = mPatients.get(position);
        holder.bind(patient);
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView phoneTextView;
        private RoundedImageView personImageView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            personImageView = itemView.findViewById(R.id.personImageView);
            itemView.setOnClickListener(this);
        }

        public void bind(Patient patient) {
            mNameTextView.setText(patient.getNom() + " " + patient.getPrenom());
            phoneTextView.setText(String.valueOf(patient.getNumeroTelephone()));
            if (patient.getImageUrl() != null && !patient.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(patient.getImageUrl())
                        .circleCrop()
                        .into(personImageView);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(mPatients.get(position)); // Passer l'objet Patient
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Patient patient);
    }

    // Pas besoin de redéclarer mListener ici, car il a déjà été déclaré en tant que champ de classe.
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}