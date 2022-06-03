package com.example.iot_java_mobile.Adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Map;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    Product.Details details;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView detail_text;
        //        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail_text = itemView.findViewById(R.id.detail_item_text);


//            logo = itemView.findViewById(R.id.home_product_image);
        }
    }

    public DetailAdapter(Product.Details details) {
        this.details = details;
        if (details != null){
            Log.e("Don", "nnnnnnnnnnnn");
            Log.e("Don", "nnnnnnnnnnnnottttt nulll, "+ this.details.toString() );
        }else{
            Log.e("Don", "It is nullllllll");
        }

//        detail_string.add("price: 300");
//        detail_string.add("ingredients: cherryberry, strawberry");
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_item, parent, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = (String) this.details.querys.keySet().toArray()[position];
        String value = (String) this.details.querys.values().toArray()[position];
        String row = key + ": "+ value;
        holder.detail_text.setText(row);
    }

    @Override
    public int getItemCount() {
        return this.details.querys.size();
    }
}
