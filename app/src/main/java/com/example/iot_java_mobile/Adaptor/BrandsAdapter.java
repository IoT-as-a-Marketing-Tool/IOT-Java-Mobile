package com.example.iot_java_mobile.Adaptor;

import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Brands;
import com.example.iot_java_mobile.R;

import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder>{
    public List<Brands> brandList;

    public BrandsAdapter(List<Brands> brandList){
        this.brandList = brandList;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_brand_name);
            logo = itemView.findViewById(R.id.home_brand_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_brand_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(this.brandList.get(position).getTitle());
        holder.logo.setImageURI(Uri.parse(this.brandList.get(position).getBrandLogo()));
    }

    @Override
    public int getItemCount() {
        return this.brandList.size();
    }
}
