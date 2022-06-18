package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Adaptor.AdCampaignProductAdapter;
import com.example.iot_java_mobile.Adaptor.AdEstablishmentAdapter;
import com.example.iot_java_mobile.Adaptor.AdsAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdEstablishmentsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_establishments_page);
        Intent intent = getIntent();
        //TODO: GET EST from CAMPAIGN id
        List<Establishment> establishments = (List<Establishment>) intent.getSerializableExtra("GivingCampaignEstablishments");
        Establishment curEstablishment = (Establishment) intent.getSerializableExtra("GivingCurEstablishment");
        AdCampaign campaign = (AdCampaign) intent.getSerializableExtra("GivingCampaign");
        ConstraintLayout constraintLayout = findViewById(R.id.current_est_constraintLayout);
        int currentId = -123;

        APIInterface apiInterface = APIClient.getRetrofitClient();
        apiInterface.getProduct(campaign.getProduct(),MainActivity.custID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Product product = response.body();
                    TextView productName = findViewById(R.id.est_cur_product_name);
                    productName.setText(product.getName());
                    ImageView productImage = findViewById(R.id.est_cur_product_image);
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AdEstablishmentsPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        if(curEstablishment == null){
            constraintLayout.setVisibility(View.GONE);
        }else{
            TextView currentEstName = findViewById(R.id.est_cur_est_name);

            ImageView adImage = findViewById(R.id.est_cur_ad_image);


//            TextView currentEstName = findViewById(R.id.est_cur_est_name);
            currentEstName.setText(curEstablishment.getName());
            currentEstName.setText(curEstablishment.getName());

            Picasso.get().load(campaign.getAd().getAd_image()).into(adImage);
            currentId = curEstablishment.getId();

        }

        TextView campaignNameTV = findViewById(R.id.est_campaign_name);
        campaignNameTV.setText(campaign.getName());

        RecyclerView establishmentsRecyclerView = findViewById(R.id.est_close_establishments);
        List<Establishment> establishmentList = getEstablishmentsInOrderDistance(currentId, establishments, MainActivity.latitude, MainActivity.longitude);
        if(establishmentList == null){
            findViewById(R.id.closer_to_you).setVisibility(View.GONE);
        }
        AdEstablishmentAdapter establishmentAdapter = new AdEstablishmentAdapter(establishmentList);
        RecyclerView.LayoutManager layoutManagerEst = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        establishmentsRecyclerView.setLayoutManager(layoutManagerEst);
        establishmentsRecyclerView.setAdapter(establishmentAdapter);
    }
    
    public List<Establishment> getEstablishmentsInOrderDistance(int currentId, List<Establishment> establishmentList, double latitude, double longitude){
        ArrayList<Establishment> distanceEstablishmentList = new ArrayList<>();
        for(Establishment est : establishmentList){
            if(est.getId() == currentId){
                continue;
            }
            double distance = calculateDistance(est.getLocation().getLatitude(), est.getLocation().getLongitude(), latitude, longitude);
            est.setDistanceFromPhone(distance);
            distanceEstablishmentList.add(est);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            distanceEstablishmentList.sort(new Comparator<Establishment>() {
                @Override
                public int compare(Establishment establishment, Establishment t1) {
                    if(establishment.getDistanceFromPhone()==t1.getDistanceFromPhone())
                        return 0;
                    else if((establishment.getDistanceFromPhone()>t1.getDistanceFromPhone()))
                        return 1;
                    else
                        return -1;
                }
            });
        }
        for (Establishment e: distanceEstablishmentList) {
            Log.e("Don", " Distancess"+ e.getDistanceFromPhone() +" Establishment: "+ e.getName() );
        }

        return distanceEstablishmentList;
    }

    private double calculateDistance(double latitude, double longitude, double latitude1, double longitude1) {
        return Math.sqrt(Math.pow((latitude - latitude1),2) +Math.pow((longitude-longitude1),2));
    }
}