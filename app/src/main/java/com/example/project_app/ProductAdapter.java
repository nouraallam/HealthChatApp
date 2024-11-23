package com.example.project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        // Adaptation des champs de texte pour afficher les données du produit
        holder.txtProductName.setText("Name: " + product.getName());
        holder.txtProductDescription.setText("Description: " + product.getDescription());
        holder.txtProductPrice.setText("Price: " + String.valueOf(product.getPrice()));
        holder.txtProductCategory.setText("Category: " + product.getCategory());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Champs de texte pour afficher les données du produit
        TextView txtProductName;
        TextView txtProductDescription;
        TextView txtProductPrice;
        TextView txtProductCategory;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialisation des champs de texte
            txtProductName = itemView.findViewById(R.id.uploadTopic);
            txtProductDescription = itemView.findViewById(R.id.uploadDesc);
            txtProductPrice = itemView.findViewById(R.id.uploadLang);
            txtProductCategory = itemView.findViewById(R.id.uploadesc);
        }
    }
}
