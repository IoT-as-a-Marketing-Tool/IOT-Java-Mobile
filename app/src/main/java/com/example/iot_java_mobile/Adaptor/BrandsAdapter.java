package com.example.iot_java_mobile.Adaptor;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder>{
    public List<Brand> brandList;
    private static ClickListener clickListener;
    public BrandsAdapter(List<Brand> brandList){
        this.brandList = brandList;
    }


    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView name;
        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_brand_name);
            itemView.setOnClickListener(this);
            logo = itemView.findViewById(R.id.home_brand_image);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
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
        holder.name.setText(this.brandList.get(position).getName());
        Picasso.get().load(this.brandList.get(position).getLogo()).into(holder.logo);
//        URL url = null;
//        try {
//            url = new URL(this.brandList.get(position).getLogo());
//            InputStream content = (InputStream)url.getContent();
//            Drawable d = Drawable.createFromStream(content , "src");
//            holder.logo.setImageDrawable(d);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        holder.logo.setImageDrawable();
//        holder.logo.setImageURI(Uri.parse(this.brandList.get(position).getBrandLogo()));
    }

    @Override
    public int getItemCount() {
        return this.brandList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        BrandsAdapter.clickListener = clickListener;
    }
}
