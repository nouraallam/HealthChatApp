package com.example.project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiscountedAdapter extends RecyclerView.Adapter<DiscountedAdapter.DiscountedViewHolder> {

    private Context mContext;
    private int[] mImageIds = {R.drawable.discountberry, R.drawable.discountbrocoli, R.drawable.discountmeat};

    public DiscountedAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public DiscountedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.discounted_row_items, parent, false);
        return new DiscountedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountedViewHolder holder, int position) {
        // Charger les images dans chaque ImageView
        holder.imageViewDiscounted.setImageResource(mImageIds[position]);
    }

    @Override
    public int getItemCount() {
        return mImageIds.length;
    }

    public static class DiscountedViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewDiscounted;

        public DiscountedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDiscounted = itemView.findViewById(R.id.imageView5);
        }
    }
}
