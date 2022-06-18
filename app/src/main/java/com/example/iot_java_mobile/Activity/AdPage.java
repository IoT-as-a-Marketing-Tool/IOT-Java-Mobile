package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.AdItem;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdPage extends AppCompatActivity {


    public String toSixHex(String hex){

        if(hex.length()==4){
            hex = hex.replaceAll("#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])", "#$1$1$2$2$3$3");

        }
        return hex;
    }
    public void positionElements(ConstraintLayout constraintLayout, float x, float y, Integer id){
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        set.setHorizontalBias(id, x);
        set.setVerticalBias(id,y);
        set.applyTo(constraintLayout);
    }
    public float nearestVal(double a){
        float val=(float) (50+  a)/100;
        if(val>1 ){
            val= 1;
        }else if (val<0){
            val= 0;
        }
        return val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_page);

//        String EXTRA_MESSAGE = "AdAttached";
//        Intent intent = getIntent();
//        AdCampaign adCampaign = (AdCampaign) intent.getSerializableExtra(EXTRA_MESSAGE);
//        Ad ad = adCampaign.getAd();


//        TextView productName = findViewById(R.id.product_name);
//        ImageView adImage = findViewById(R.id.ad_image);
//        Picasso.get().load(ad.getAd_image()).into(adImage);
//




    }
}