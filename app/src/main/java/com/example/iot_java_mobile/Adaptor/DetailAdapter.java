package com.example.iot_java_mobile.Adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.R;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    JsonElement details;
    ArrayList<String> detail_string= new ArrayList<String>();
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView detail_text;
        //        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail_text = itemView.findViewById(R.id.detail_item_text);


//            logo = itemView.findViewById(R.id.home_product_image);
        }
    }

    public DetailAdapter(JsonElement details) {
        this.details = details;
        if (details != null){
            Log.e("Don", "nnnnnnnnnnnn");
            Log.e("Don", "nnnnnnnnnnnn" + details.getAsString());
        }else{
            Log.e("Don", "It is nullllllll");
        }
        detail_string.add("price: 300");
        detail_string.add("ingredients: cherryberry, strawberry");
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_item, parent, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detail_text.setText(this.detail_string.get(position));
    }

    @Override
    public int getItemCount() {
        return this.detail_string.size();
    }
}
