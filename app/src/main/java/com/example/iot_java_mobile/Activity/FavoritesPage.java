package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.iot_java_mobile.Adaptor.FavoriteBrandAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesPage extends AppCompatActivity {
    List<Brand> favoriteBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_page);
        APIInterface apiInterface = APIClient.getRetrofitClient();
        favoriteBrands = new ArrayList<>();



        FavoriteBrandAdapter favoriteBrandAdapter = new FavoriteBrandAdapter(favoriteBrands);
        RecyclerView recyclerView = findViewById(R.id.favorited_brand_recycler_view);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerProduct);
        recyclerView.setAdapter(favoriteBrandAdapter);

        apiInterface.getBrands().enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if(response.isSuccessful() && response.body()!= null){
                    favoriteBrands.addAll(response.body());
                    favoriteBrands.addAll(response.body());
                    favoriteBrands.addAll(response.body());
                    favoriteBrands.addAll(response.body());

                    favoriteBrandAdapter.notifyDataSetChanged();
                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
                }


            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Toast.makeText(FavoritesPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}