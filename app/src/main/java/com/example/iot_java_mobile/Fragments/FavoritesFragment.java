package com.example.iot_java_mobile.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.FavoritesPage;
import com.example.iot_java_mobile.Activity.MainActivity;
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


public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }

    List<Brand> favoriteBrands;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoriteBrands = new ArrayList<>();
        APIInterface apiInterface = APIClient.getRetrofitClient();
        Log.e("Don", "onCreateView: "+ MainActivity.custID );

        FavoriteBrandAdapter favoriteBrandAdapter = new FavoriteBrandAdapter(favoriteBrands);
        RecyclerView recyclerView = v.findViewById(R.id.favorited_brand_recycler_view);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerProduct);
        recyclerView.setAdapter(favoriteBrandAdapter);

        apiInterface.getFavoriteBrands(MainActivity.custID).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Log.e("Don", "onResponse: "+ response.body() );
                    favoriteBrands.addAll(response.body());
                    favoriteBrandAdapter.notifyDataSetChanged();
//                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
                }


            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



        return v;
    }
}