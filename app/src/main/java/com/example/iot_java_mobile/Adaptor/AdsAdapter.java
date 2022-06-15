package com.example.iot_java_mobile.Adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.AdEstablishmentsPage;
import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.BrandPage;
import com.example.iot_java_mobile.Activity.ProductPage;
import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder>{
    public List<Establishment> establishmentList;
    public List<AdCampaign> campaignList;
    private static AdsAdapter.ClickListener clickListener;
    public View.OnClickListener establishmentClickListener;


    public AdsAdapter(List<AdCampaign> campaignList, List<Establishment> establishmentList){
        this.campaignList = campaignList;
        this.establishmentList = establishmentList;
    }



    @NonNull
    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ad_page, parent, false);
        return new AdsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAdapter.ViewHolder holder, int position) {
        AdCampaign campaign = this.campaignList.get(position);
        String establishment_name = (this.establishmentList.size()>0)? this.establishmentList.get(position).getName(): "View all Establishments";
        Ad ad = campaign.getAd();
        holder.establishment_name.setText(establishment_name);
        Picasso.get().load(ad.getAd_image()).into(holder.ad_image);
        int i = holder.getAdapterPosition();


//        holder.establishment_name.setOnClickListener(establishmentClickListener);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView establishment_name;
        public ImageView ad_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            establishment_name = itemView.findViewById(R.id.ad_establishment);
            ad_image = itemView.findViewById(R.id.ad_image);
//            itemView.setOnClickListener(establishment_name);
            establishment_name.setOnClickListener(this::onClick);


        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onClickListener()
    }
    public void setOnItemClickListener(AdsAdapter.ClickListener clickListener) {
        AdsAdapter.clickListener = clickListener;
    }
}
