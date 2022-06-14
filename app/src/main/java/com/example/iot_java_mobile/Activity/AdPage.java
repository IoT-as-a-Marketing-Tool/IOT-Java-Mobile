package com.example.iot_java_mobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

public class AdPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_page);


        Intent intent = getIntent();
        AdCampaign adCampaign = (AdCampaign) intent.getSerializableExtra("GivingAdCampaign");
        String  establishment_name =  intent.getStringExtra("GivingEstablishment");
        Ad ad = adCampaign.getAd();

//        TextView productName = findViewById(R.id.product_name);
        ImageView adImage = findViewById(R.id.ad_image);
        Picasso.get().load(ad.getAd_image()).into(adImage);

        TextView establishment_text = findViewById(R.id.ad_establishment);
        establishment_text.setText(establishment_name);


    }
}