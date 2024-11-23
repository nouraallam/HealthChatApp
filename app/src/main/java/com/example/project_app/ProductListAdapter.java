package com.example.project_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.app.AppCompatActivity;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;
    private OnProductInteractionListener listener;
    public interface OnProductInteractionListener {
        void onAddToCart(Product product);
        void onBuyNow(Product product);
    }


    public ProductListAdapter(ArrayList<Product> productList, OnProductInteractionListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produit_details_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

     class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView lblPName, lblPDesc, lblPPrice;
        ImageView btnCart;
        AppCompatButton btnBuyNow;


        ProductViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            lblPName = itemView.findViewById(R.id.lblPName);
            lblPDesc = itemView.findViewById(R.id.lblPDesc);
            lblPPrice = itemView.findViewById(R.id.lblPPrice);
            btnCart = itemView.findViewById(R.id.btnCart);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);

            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onAddToCart(productList.get(position));

                    }
                }
            });

            btnBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onBuyNow(productList.get(position));
                    }
                }
            });


        }

        void bind(Product product) {
            Glide.with(imgProduct.getContext()).load(product.getImageUrl()).into(imgProduct);
            lblPName.setText(product.getName());
            lblPDesc.setText(product.getDescription());
            lblPPrice.setText(product.getPrice() + " DH"); // Ajouter " DH" à côté du prix
        }

    }
}
