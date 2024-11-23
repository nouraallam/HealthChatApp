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

public class DoctorProductListAdapter extends RecyclerView.Adapter<DoctorProductListAdapter.DoctorProductViewHolder> {

    private ArrayList<Product> productList;
    private OnDoctorProductInteractionListener listener;

    public DoctorProductListAdapter(ArrayList<Product> productList, OnDoctorProductInteractionListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details_item_doctor, parent, false);
        return new DoctorProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnDoctorProductInteractionListener {
        void onDoctorAddToCart(Product product);
        void onDoctorBuyNow(Product product);
    }

    static class DoctorProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView lblPNamedoctor;
        TextView lblPDescdoctor;
        TextView lblPPricedoctor;
        ImageView btnCartdoctor;
        View btnBuyNowdcotor;

        DoctorProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            lblPNamedoctor = itemView.findViewById(R.id.lblPNamedoctor);
            lblPDescdoctor = itemView.findViewById(R.id.lblPDescdoctor);
            lblPPricedoctor = itemView.findViewById(R.id.lblPPricedoctor);
            btnCartdoctor = itemView.findViewById(R.id.btnCartdoctor);
            btnBuyNowdcotor = itemView.findViewById(R.id.btnBuyNowdcotor);
        }

        void bind(final Product product, final OnDoctorProductInteractionListener listener) {
            lblPNamedoctor.setText(product.getName());
            lblPDescdoctor.setText(product.getDescription());
            lblPPricedoctor.setText(product.getPrice() + " DH");

            // Use Glide or other image loading library to load the product image
            Glide.with(itemView.getContext()).load(product.getImageUrl()).into(imgProduct);

            btnCartdoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDoctorAddToCart(product);
                }
            });

            btnBuyNowdcotor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDoctorBuyNow(product);
                }
            });
        }
    }
}
