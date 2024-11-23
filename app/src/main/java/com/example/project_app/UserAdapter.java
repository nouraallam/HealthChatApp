package com.example.project_app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<String> userList; // Liste pour stocker les noms d'utilisateur uniquement
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String username);
    }

    public UserAdapter(List<String> userList, OnItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_doctor_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String username = userList.get(position);
        holder.bind(username, listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.usernameTextView);
        }

        public void bind(final String username, final OnItemClickListener listener) {
            userNameTextView.setText(username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(username);
                }
            });
        }
    }
}
