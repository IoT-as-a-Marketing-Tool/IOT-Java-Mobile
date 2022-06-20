package com.example.iot_java_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Adaptor.AdCampaignProductAdapter;
import com.example.iot_java_mobile.Adaptor.DetailAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View v =  inflater.inflate(R.layout.fragment_product, container, false);
        String EXTRA_MESSAGE = "GivingProduct";
        Bundle bundle = getArguments();
//        Brand brand = (Brand) intent.getSerializableExtra(EXTRA_MESSAGE+"GivingBrand");
        Product product = (Product) bundle.getSerializable(EXTRA_MESSAGE);
        Log.e("Don", "onCreate: product is "+product.toString() );
//        String EXTRA_MESSAGE = "BrandAttached";
//        Intent intent = new Intent(MainActivity.getContext(), BrandPage.class);
//        Brand brand = brandList.get(position);
//        intent.putExtra(EXTRA_MESSAGE, brand);
//        startActivity(intent);
        TextView brandName = v.findViewById(R.id.product_brand_name);
        final Brand[] brand = {null};
        APIInterface apiInterface = APIClient.getRetrofitClient();
        apiInterface.getBrand(product.getBrand(), MainActivity.custID).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if(response.isSuccessful() && response.body()!= null){
                    brand[0] = response.body();
                    Log.e("Don", "onResponse: brand == "+ brand[0].getName() );
                    if (brand[0] != null){
                        brandName.setText(brand[0].getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                Toast.makeText(getContext(), "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        TextView productName = v.findViewById(R.id.product_name);
        ImageView productImage = v.findViewById(R.id.product_image);


//        Log.e("Don", "onCreate: Ad campaign"+ product.getAdcampaigns().get(0) );
        //TODO: get Logo
        //TODO: make brand clickable and go to brand
        productName.setText(product.getName());
        Picasso.get().load(product.getImage()).into(productImage);


        RecyclerView detailRecyclerView = v.findViewById(R.id.product_detail_recycler_view);
        DetailAdapter detailAdapter = new DetailAdapter(product.getDetails());
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        detailRecyclerView.setLayoutManager(layoutManagerProduct);
        detailRecyclerView.setAdapter(detailAdapter);


        RecyclerView adCampaignRecyclerView = v.findViewById(R.id.product_ad_recycler_view);
        AdCampaignProductAdapter campaignProductAdapter = new AdCampaignProductAdapter(product.getAdcampaigns());
        RecyclerView.LayoutManager layoutManagerAdCampaign = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        adCampaignRecyclerView.setLayoutManager(layoutManagerAdCampaign);
        adCampaignRecyclerView.setAdapter(campaignProductAdapter);
        campaignProductAdapter.setOnItemClickListener(new AdCampaignProductAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String EXTRA_MESSAGE = "GivingCampaignList";

                Intent intent = new Intent(getContext(), AdsPage.class);
                List<AdCampaign> adCampaignList = new ArrayList<>();
                adCampaignList.add(product.getAdcampaigns().get(position));
                intent.putExtra(EXTRA_MESSAGE, (Serializable) adCampaignList);
                List<Establishment> establishmentList = new ArrayList<>();
//                for (int i = 0; i < adCampaignList.size(); i++){
//                    establishmentNameList.add("View all Establishments");
//                }
                intent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);

                startActivity(intent);
            }
        });
        Button viewAds = v.findViewById(R.id.view_ads_btn);
        Button backBtn = v.findViewById(R.id.button_back_product);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SessionManager sessionManager = new SessionManager(getContext());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, new HomeFragment(sessionManager)).commit();


                    }}
        );
        viewAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EXTRA_MESSAGE = "GivingCampaignList";
                Intent intent = new Intent(getContext(), AdsPage.class);
                List<AdCampaign> adCampaignList = product.getAdcampaigns();
                if(adCampaignList== null || adCampaignList.size()==0)
                    return;
                intent.putExtra(EXTRA_MESSAGE, (Serializable) adCampaignList);
                List<Establishment> establishmentList = new ArrayList<>();
//                for (int i = 0; i < adCampaignList.size(); i++){
//                    establishmentNameList.add("View all Establishments");
//                }
                intent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);

                startActivity(intent);
            }
        });
        return v;
    }
}