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

public class DoctorCartListAdapter extends RecyclerView.Adapter<DoctorCartListAdapter.DoctorCartViewHolder> {

    private ArrayList<DoctorCartItem> cartItemList;

    public DoctorCartListAdapter(ArrayList<DoctorCartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public DoctorCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_doctor, parent, false);
        return new DoctorCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorCartViewHolder holder, int position) {
        DoctorCartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class DoctorCartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView lblPName, lblPPrice, lblPQuantity;

        DoctorCartViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            lblPName = itemView.findViewById(R.id.lblPNameCartdoctor);
            lblPPrice = itemView.findViewById(R.id.lblPPriceCartdoctor);
            lblPQuantity = itemView.findViewById(R.id.lblPQuantityCartdoctor);
        }

        void bind(DoctorCartItem cartItem) {
            Glide.with(imgProduct.getContext()).load(cartItem.getProduct().getImageUrl()).into(imgProduct);
            lblPName.setText(cartItem.getProduct().getName());
            lblPPrice.setText(cartItem.getProduct().getPrice() + " DH");
            lblPQuantity.setText("Quantity: " + cartItem.getQuantity());
        }
    }
}
