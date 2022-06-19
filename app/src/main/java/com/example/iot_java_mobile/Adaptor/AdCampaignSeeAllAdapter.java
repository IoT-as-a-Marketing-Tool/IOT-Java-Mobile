package com.example.iot_java_mobile.Adaptor;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.AdCampaign;

import java.util.ArrayList;
import java.util.List;

public class AdCampaignSeeAllAdapter extends RecyclerView.Adapter<AdCampaignSeeAllAdapter.ViewHolder> {
    public List<AdCampaign> campaignList ;

    public AdCampaignSeeAllAdapter(List<AdCampaign> campaignList) {
        this.campaignList = campaignList;
        if (this.campaignList == null){
            this.campaignList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public AdCampaignSeeAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdCampaignSeeAllAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
