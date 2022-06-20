package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.AutoScrollHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.iot_java_mobile.Adaptor.AdsAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsPage extends AppCompatActivity {
    List<AdCampaign> campaignList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String EXTRA_MESSAGE = "GivingEstProducts";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_page);
        SessionManager sessionManager = new SessionManager(this);
        String token= "Bearer "+ sessionManager.getAuthToken();

        Intent intent = getIntent();
//        List<EstProduct> estProductList = (List<EstProduct>) intent.getSerializableExtra(EXTRA_MESSAGE);
//        if (estProductList != null){
//            Log.e("Don", "onCreate: estProducts == "+ estProductList );
//            AdsAdapter adsAdapter = new AdsAdapter(estProductList);
//        }
        campaignList = (List<AdCampaign>) intent.getSerializableExtra("GivingCampaignList");
        List<Establishment> establishmentList = (List<Establishment>) intent.getSerializableExtra("GivingEstablishmentNameList");
        List<Integer> campaignIds = (List<Integer>) intent.getSerializableExtra("UnFavoriteCampaignList");
        APIInterface apiInterface = APIClient.getRetrofitClient();
        if(campaignIds == null){
            campaignIds = new ArrayList<>();
        }
        for(Integer i: campaignIds){
            apiInterface.deleteNotification(i, MainActivity.custID, token).enqueue(
                    new Callback<List<AdCampaign>>() {
                        @Override
                        public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {
                            if(response.isSuccessful() && response.body()!= null){
                                Log.e("Don", "succesfully deleted notification: "+ response.body() );

//                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<AdCampaign>> call, Throwable t) {

                        }
                    }
            );
        }


        Log.e("Don", "onCreate: campaignList == "+ campaignList );
        AdsAdapter adsAdapter = new
                AdsAdapter(campaignList, establishmentList );

        RecyclerView recyclerView = findViewById(R.id.ads_recycler_view);
        LinearLayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerProduct);
        recyclerView.setAdapter(adsAdapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(layoutManagerProduct.findLastCompletelyVisibleItemPosition() < (adsAdapter.getItemCount() - 1)){
                    layoutManagerProduct.smoothScrollToPosition(recyclerView, new RecyclerView.State(), layoutManagerProduct.findLastVisibleItemPosition()+1);
                }
            }
        },0,6500);
        adsAdapter.setOnItemClickListener(
                new AdsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.e("Don", "onItemClick: " +v );
                        switch (v.getId()){
                            case R.id.ad_establishment:
                                Log.e("Don", "--- establishments clicked" );
                                List<Establishment> establishments = campaignList.get(position).getEstablishments();
                                Intent intent = new Intent(AdsPage.this, AdEstablishmentsPage.class);
                                intent.putExtra("GivingCampaignEstablishments", (Serializable) establishments);
                                Establishment curEstablishment = null;
                                if (establishmentList.size() > position){
                                    curEstablishment = establishmentList.get(position);
                                }
                                intent.putExtra("GivingCurEstablishment", (Serializable) curEstablishment);
                                intent.putExtra("GivingCampaign", campaignList.get(position));

                                startActivity(intent);
                                break;
                        }
                    }
                }
        );
//        adsAdapter.setOnItemClickListener(new AdsAdapter.ClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                v.
//            }
//
//            @Override
//            public void onClickListener() {
//
//            }
//        });

        // setScrollingTouchSlop();










    }
}