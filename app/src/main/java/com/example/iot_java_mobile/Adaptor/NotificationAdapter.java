package com.example.iot_java_mobile.Adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<AdCampaign> campaignList;
    
    public NotificationAdapter(List<AdCampaign> campaignList) {
        this.campaignList = campaignList;
        if (this.campaignList == null){
            this.campaignList = new ArrayList<>();
        }
    }
    private static ClickListener clickListener;
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        AdCampaign campaign = campaignList.get(position);
        holder.campaignName.setText(campaign.getName());
        Picasso.get().load(campaign.getAd().getAd_image()).into(holder.adImage);

    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView campaignName;
        ImageView adImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            campaignName = itemView.findViewById(R.id.notification_campaign_name);
            adImage = itemView.findViewById(R.id.notification_ad_image);
            
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
    public void setOnItemClickListener(NotificationAdapter.ClickListener clickListener) {
        NotificationAdapter.clickListener = clickListener;
    }
}
