package com.example.project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentlyAddedAdapter extends RecyclerView.Adapter<RecentlyAddedAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    private int[] mImageIds;

    public RecentlyAddedAdapter(Context context, List<Product> productList, int[] imageIds) {
        this.context = context;
        this.productList = productList;
        this.mImageIds = imageIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_viewed_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, mImageIds[position % mImageIds.length]);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView lblProducrName, lblDescr, lblPrice, lblUnit,lblQty;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            lblProducrName = itemView.findViewById(R.id.lblProducrName);
            lblDescr = itemView.findViewById(R.id.lblDescr);
            lblPrice = itemView.findViewById(R.id.lblPrice);
            lblQty=itemView.findViewById(R.id.lblQty);
            lblUnit = itemView.findViewById(R.id.lblUnit);
        }

        public void bind(Product product, int imageId) {
            lblProducrName.setText(product.getName());
            lblDescr.setText(product.getDescription());
            lblPrice.setText(product.getPrice() + " DH");
            lblQty.setText("Qty: " + product.getQty());
            lblUnit.setText(product.getUnit());

            // Set image resource dynamically
            imageView.setImageResource(imageId);
        }
    }
}
