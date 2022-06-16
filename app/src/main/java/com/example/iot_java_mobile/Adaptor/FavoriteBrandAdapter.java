package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteBrandAdapter extends RecyclerView.Adapter<FavoriteBrandAdapter.ViewHolder> {
    List<Brand> brandList;

    public FavoriteBrandAdapter(List<Brand> brandList) {
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public FavoriteBrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_brands, parent, false);
        return new FavoriteBrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteBrandAdapter.ViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        holder.brandDescription.setText(brand.getDescription());
        Picasso.get().load(brand.getLogo()).into(holder.brandLogo);
        holder.removeFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: REMOVE BRAND
                brandList.remove(holder.getAdapterPosition());
//                holder.constraintLayout. setVisibility(View.GONE);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView brandName;
        TextView brandDescription;
        ImageView brandLogo;
        Button removeFavBtn;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.fav_brand_name);
            brandDescription = itemView.findViewById(R.id.fav_brand_desc);
            brandLogo = itemView.findViewById(R.id.fav_brand_logo);
            removeFavBtn = itemView.findViewById(R.id.remove_fav_brand);
            constraintLayout = itemView.findViewById(R.id.fav_brand_constraint_layout);

        }
    }
}
