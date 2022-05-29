package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.iot_java_mobile.Adaptor.BrandsAdapter;
import com.example.iot_java_mobile.Domain.Brands;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ImageSlider imageSlider;
    private TextView homeBrand;
    private TextView homeAd;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewHomeBrands;
    private List<Brands> brandsList;
    BrandsAdapter brandAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSlider = findViewById(R.id.carousel);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add( new SlideModel("https://thumbs.dreamstime.com/z/mango-juice-ads-liquid-hand-banner-grabbing-fruit-effect-blue-sky-background-d-illustration-152914246.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://www.sunchipsethiopia.net/img/photo_2021-04-27_15-48-07.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://www.logogenie.net/images/articles/brands-of-the-world-coca-cola.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://world.openfoodfacts.org/images/products/878/456/290/8364/front_fr.3.full.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

//        homeBrand = findViewById(R.id.homeBrand);
//        homeBrand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, brand.class));
//            }
//        });

//        homeAd = findViewById(R.id.homeAd);
//        homeAd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Ad.class));
//            }
//        });
        brandsList = new ArrayList<Brands>();

        fetchData();

        brandAdapter = new BrandsAdapter(brandsList);
        recyclerViewHomeBrands = findViewById(R.id.home_brand_list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeBrands.setLayoutManager(layoutManager);
        recyclerViewHomeBrands.setAdapter(brandAdapter);




//        recyclerViewBrands();
    }

    private void fetchData() {
        APIClient.getRetrofitClient().getBrands().enqueue(new Callback<Brands>() {
            @Override
            public void onResponse(Call<Brands> call, Response<Brands> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Log.e("Don", "SUCCESSSSS ");
                    Log.e("Don", "SUCCESSSSS ");
                    Log.e("Don", "SUCCESSSSS ");

                    Log.e("Don", "SUCCESSSSS " + response.body());
                    brandsList.addAll(Collections.singleton(response.body()));
                    brandAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<Brands> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

//    private void recyclerViewBrands() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewBrands.setLayoutManager(linearLayoutManager);
//
//        ArrayList<BrandsDomain> categoryBrand = new ArrayList<>();
//        categoryBrand.add(new BrandsDomain("Sunchips", "sunchips"));
//        categoryBrand.add(new BrandsDomain("Mango Juice", "mangojuice"));
//        categoryBrand.add(new BrandsDomain("Arada", "arada"));
//        categoryBrand.add(new BrandsDomain("Moya", "moya"));
//        categoryBrand.add(new BrandsDomain("Waffers", "waffers"));
//    }

}