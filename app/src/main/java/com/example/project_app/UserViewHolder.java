package com.example.project_app;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import android.widget.TextView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView usernameTextView;
    private ImageView doctorImageView;
    private List<Users> userList; // Déclaration de la liste des utilisateurs
    private UserAdapter.OnItemClickListener listener;

    public UserViewHolder(@NonNull View itemView, UserAdapter.OnItemClickListener listener, List<Users> userList) {
        super(itemView);
        this.listener = listener;
        this.userList = userList; // Initialisation de la liste des utilisateurs
        usernameTextView = itemView.findViewById(R.id.usernameTextView);
        doctorImageView = itemView.findViewById(R.id.doctor_item_image);

        // Ajouter un OnClickListener à l'image du médecin
        doctorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    Users user = userList.get(position);
                    String username = user.getUserName(); // Récupérer le nom d'utilisateur
                    listener.onItemClick(username); // Passer le nom d'utilisateur à onItemClick
                }
            }
        });
    }

    public void bind(Users user) {
        usernameTextView.setText(user.getUserName());

        // Charger l'image à partir de Firebase Storage et l'afficher dans ImageView
        String imageUrl = user.getProfilepic(); // Assurez-vous d'avoir la méthode getProfilepic() dans votre modèle Users
        Picasso.get().load(imageUrl).into(doctorImageView);
    }
}
