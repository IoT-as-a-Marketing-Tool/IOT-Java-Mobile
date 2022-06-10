package com.example.iot_java_mobile.Adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdCampaignProductAdapter extends RecyclerView.Adapter<AdCampaignProductAdapter.ViewHolder> {
    public List<AdCampaign> campaignList ;
    private static AdCampaignProductAdapter.ClickListener clickListener;
    public AdCampaignProductAdapter(List<AdCampaign> campaignList){
        if (campaignList!= null){
            this.campaignList = campaignList;
        }else{
            this.campaignList = new ArrayList<AdCampaign>();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView adImage;
//        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            adImage = itemView.findViewById(R.id.ad_campaign_ad_image);
//            text = itemView.findViewById(R.id.remove_soon);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

    }

    @NonNull
    @Override
    public AdCampaignProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adcampaign_items, parent, false);
        return new AdCampaignProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdCampaignProductAdapter.ViewHolder holder, int position) {
        //TODO: set Image
        Log.e("Don", "onBindViewHolder: is this even visible" );
        Log.e("Don", "onBindViewHolder: "+ campaignList.get(position) );
//        holder.text.setText("helllo");
        Ad ad = campaignList.get(position).getAd();
        if (ad != null){
            Picasso.get().load(ad.getAd_image()).into(holder.adImage);
        }


    }

    @Override
    public int getItemCount() {
        return this.campaignList.size();
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(AdCampaignProductAdapter.ClickListener clickListener) {
        AdCampaignProductAdapter.clickListener = clickListener;
    }

}
