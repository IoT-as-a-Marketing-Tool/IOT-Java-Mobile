package com.example.iot_java_mobile.Adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.BrandPage;
import com.example.iot_java_mobile.Activity.ProductPage;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsHomeAdapter extends RecyclerView.Adapter<ProductsHomeAdapter.ViewHolder>{
    public List<Product> productList;
    private static ProductsHomeAdapter.ClickListener clickListener;

    public ProductsHomeAdapter(List<Product> productList){
        this.productList = productList;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_product_name);
            image = itemView.findViewById(R.id.home_product_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(this.productList.get(position).getName());
        Picasso.get().load(this.productList.get(position).getImage()).into(holder.image);

//        holder.logo.setImageDrawable();
//        holder.logo.setImageURI(Uri.parse(this.brandList.get(position).getBrandLogo()));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(ProductsHomeAdapter.ClickListener clickListener) {
        ProductsHomeAdapter.clickListener = clickListener;
    }
    
}
