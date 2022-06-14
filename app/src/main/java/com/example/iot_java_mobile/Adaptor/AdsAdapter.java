package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder>{
    public List<EstProduct> estProductList;


    public AdsAdapter(List<EstProduct> estProductList) {
        this.estProductList = estProductList;
    }

    @NonNull
    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ad_page, parent, false);
        return new AdsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAdapter.ViewHolder holder, int position) {
        AdCampaign campaign = this.estProductList.get(position).getCampaign();
        Establishment establishment = this.estProductList.get(position).getEstablishment();
        Ad ad = campaign.getAd();
        holder.establishment_name.setText(establishment.getName());
        Picasso.get().load(ad.getAd_image()).into(holder.ad_image);
    }

    @Override
    public int getItemCount() {
        return estProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView establishment_name;
        ImageView ad_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            establishment_name = itemView.findViewById(R.id.ad_establishment);
            ad_image = itemView.findViewById(R.id.ad_image);
        }

    }
}
