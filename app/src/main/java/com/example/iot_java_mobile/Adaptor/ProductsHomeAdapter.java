package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;

import java.util.List;

public class ProductsHomeAdapter extends RecyclerView.Adapter<ProductsHomeAdapter.ViewHolder>{
    public List<Product> productList;

    public ProductsHomeAdapter(List<Product> productList){
        this.productList = productList;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        //        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_product_name);
//            logo = itemView.findViewById(R.id.home_product_image);
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
//        holder.logo.setImageDrawable();
//        holder.logo.setImageURI(Uri.parse(this.brandList.get(position).getBrandLogo()));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }
}
