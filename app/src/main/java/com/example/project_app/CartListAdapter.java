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

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItemList;

    public CartListAdapter(ArrayList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView lblPName, lblPPrice, lblPQuantity;

        CartViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            lblPName = itemView.findViewById(R.id.lblPNameCart);
            lblPPrice = itemView.findViewById(R.id.lblPPriceCart);
            lblPQuantity = itemView.findViewById(R.id.lblPQuantityCart);
        }

        void bind(CartItem cartItem) {
            Glide.with(imgProduct.getContext()).load(cartItem.getProduct().getImageUrl()).into(imgProduct);
            lblPName.setText(cartItem.getProduct().getName());
            lblPPrice.setText(cartItem.getProduct().getPrice() + " DH");
            lblPQuantity.setText("Quantity: " + cartItem.getQuantity());
        }
    }
}
