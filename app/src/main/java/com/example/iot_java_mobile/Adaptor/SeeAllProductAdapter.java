package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeeAllProductAdapter extends RecyclerView.Adapter<SeeAllProductAdapter.ViewHolder> {
    List<Product> productList;
    private static ClickListener clickListener;
    public SeeAllProductAdapter(List<Product> productList) {
        this.productList = productList;
        if (this.productList == null){
            this.productList = new ArrayList<>();
        }

    }

    @NonNull
    @Override
    public SeeAllProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new SeeAllProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDesc.setText(product.getDetails().toString());
        Picasso.get().load(product.getImage()).into(holder.productLogo);
        if (product.getAdcampaigns() != null && product.getAdcampaigns().size() >0){
            Picasso.get().load(product.getAdcampaigns().get(0).getAd().getAd_image()).into(holder.productAdImage);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName;
        TextView productDesc;
        ImageView productLogo;
        ImageView productAdImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_item_name);
            productLogo = itemView.findViewById(R.id.product_item_image);
            productDesc = itemView.findViewById(R.id.product_item_details);
            productAdImage = itemView.findViewById(R.id.product_item_ad_image);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(SeeAllProductAdapter.ClickListener clickListener) {
        SeeAllProductAdapter.clickListener = clickListener;
    }
}
