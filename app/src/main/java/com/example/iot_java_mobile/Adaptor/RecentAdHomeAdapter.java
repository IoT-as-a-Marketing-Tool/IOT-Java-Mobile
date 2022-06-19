package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecentAdHomeAdapter extends RecyclerView.Adapter<RecentAdHomeAdapter.ViewHolder> {
    List<AdCampaign> campaignList;
    private static ClickListener clickListener;
    public RecentAdHomeAdapter(List<AdCampaign> campaignList) {
        this.campaignList = campaignList;
        if (this.campaignList == null){
            this.campaignList = new ArrayList<>();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView name;
        ImageView adImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.home_recent_ad_image);
            itemView.setOnClickListener(this);
//            name = itemView.findViewById(R.id.h);
//            logo = itemView.findViewById(R.id.home_product_image);
        }
        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    @NonNull
    @Override
    public RecentAdHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recent_ad_items, parent, false);
        return new RecentAdHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdHomeAdapter.ViewHolder holder, int position) {
        Ad ad = campaignList.get(position).getAd();
        Picasso.get().load(ad.getAd_image()).into(holder.adImage);
    }

    @Override
    public int getItemCount() {
        return this.campaignList.size();
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(RecentAdHomeAdapter.ClickListener clickListener) {
        RecentAdHomeAdapter.clickListener = clickListener;
    }


}
