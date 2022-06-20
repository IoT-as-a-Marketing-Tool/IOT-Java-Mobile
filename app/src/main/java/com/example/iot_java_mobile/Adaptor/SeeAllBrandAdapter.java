package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Fragments.SeeAllBrandFragment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeeAllBrandAdapter extends RecyclerView.Adapter<SeeAllBrandAdapter.ViewHolder> {
    List<Brand> brandList;
    private static ClickListener clickListener;
    public SeeAllBrandAdapter(List<Brand> brandList) {
        this.brandList = brandList;
        if (this.brandList == null){
            this.brandList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public SeeAllBrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brands, parent, false);
        return new SeeAllBrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllBrandAdapter.ViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        Picasso.get().load(brand.getLogo()).into(holder.brandLogo);
        holder.brandDesc.setText(brand.getDescription());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView brandName;
        TextView brandDesc;
        ImageView brandLogo;
        Button removeBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brand_item_name);
            brandLogo = itemView.findViewById(R.id.brand_item_logo);
            brandDesc = itemView.findViewById(R.id.brand_item_desc);
            removeBtn = itemView.findViewById(R.id.brand_item_remove_btn);
            removeBtn.setVisibility(View.GONE);
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
    public void setOnItemClickListener(SeeAllBrandAdapter.ClickListener clickListener) {
        SeeAllBrandAdapter.clickListener = clickListener;
    }
}
