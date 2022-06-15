package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Adaptor.AdCampaignProductAdapter;
import com.example.iot_java_mobile.Adaptor.AdEstablishmentAdapter;
import com.example.iot_java_mobile.Adaptor.AdsAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdEstablishmentsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_establishments_page);
        Intent intent = getIntent();
        List<Establishment> establishments = (List<Establishment>) intent.getSerializableExtra("GivingCampaignEstablishments");
        Establishment curEstablishment = (Establishment) intent.getSerializableExtra("GivingCurEstablishment");
        AdCampaign campaign = (AdCampaign) intent.getSerializableExtra("GivingCampaign");
        ConstraintLayout constraintLayout = findViewById(R.id.current_est_constraintLayout);
        if(curEstablishment == null){
            constraintLayout.setVisibility(View.GONE);
        }else{
            TextView currentEstName = findViewById(R.id.est_cur_est_name);
            TextView currentEstProductName = findViewById(R.id.est_cur_product_name);
            ImageView adImage = findViewById(R.id.est_cur_ad_image);
//            TextView currentEstName = findViewById(R.id.est_cur_est_name);
            currentEstName.setText(curEstablishment.getName());
            Picasso.get().load(campaign.getAd().getAd_image()).into(adImage);

        }

        TextView campaignNameTV = findViewById(R.id.est_campaign_name);
        campaignNameTV.setText(campaign.getName());

        RecyclerView establishmentsRecyclerView = findViewById(R.id.est_close_establishments);
        AdEstablishmentAdapter establishmentAdapter = new AdEstablishmentAdapter(establishments);
        RecyclerView.LayoutManager layoutManagerEst = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        establishmentsRecyclerView.setLayoutManager(layoutManagerEst);
        establishmentsRecyclerView.setAdapter(establishmentAdapter);
    }
}