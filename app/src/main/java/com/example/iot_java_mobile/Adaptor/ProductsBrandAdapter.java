package com.example.iot_java_mobile.Adaptor;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductsBrandAdapter extends RecyclerView.Adapter<ProductsBrandAdapter.ViewHolder>{
    public List<Product> productList;

    private static ProductsBrandAdapter.ClickListener clickListener;
    public ProductsBrandAdapter(List<Product> productList){
        this.productList = productList;
        if (this.productList == null){
            this.productList = new ArrayList<>();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.brand_product_image);
            itemView.setOnClickListener(this);
//            logo = itemView.findViewById(R.id.home_product_image);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_product_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(this.productList.get(position).getImage()).into(holder.image);

//        URL url = null;
//        try {
//            url = new URL(this.productList.get(position).getImage());
//            InputStream content = (InputStream)url.getContent();
//            Drawable d = Drawable.createFromStream(content , "src");
//            holder.image.setImageDrawable(d);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //holder.logo.setImageDrawable();
//        holder.logo.setImageURI(Uri.parse(this.brandList.get(position).getBrandLogo()));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(ProductsBrandAdapter.ClickListener clickListener) {
        ProductsBrandAdapter.clickListener = clickListener;
    }
}
