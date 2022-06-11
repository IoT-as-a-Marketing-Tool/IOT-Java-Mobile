package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Adaptor.AdCampaignProductAdapter;
import com.example.iot_java_mobile.Adaptor.DetailAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: change so that it can only accept product object
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        String EXTRA_MESSAGE = "GivingProduct";
        Intent intent = getIntent();
//        Brand brand = (Brand) intent.getSerializableExtra(EXTRA_MESSAGE+"GivingBrand");
        Product product = (Product) intent.getSerializableExtra(EXTRA_MESSAGE);
        Log.e("Don", "onCreate: product is "+product.toString() );
//        String EXTRA_MESSAGE = "BrandAttached";
//        Intent intent = new Intent(MainActivity.this, BrandPage.class);
//        Brand brand = brandList.get(position);
//        intent.putExtra(EXTRA_MESSAGE, brand);
//        startActivity(intent);
        TextView brandName = findViewById(R.id.product_brand_name);
        final Brand[] brand = {null};
        APIInterface apiInterface = APIClient.getRetrofitClient(Product.Details.class, new APIClient.ProductDetailDeserializer());
        apiInterface.getBrand(product.getBrand()).enqueue(new Callback<Brand>() {
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
                Toast.makeText(ProductPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        TextView productName = findViewById(R.id.product_name);
        ImageView productImage = findViewById(R.id.product_image);


//        Log.e("Don", "onCreate: Ad campaign"+ product.getAdcampaigns().get(0) );
        //TODO: get Logo
        //TODO: make brand clickable and go to brand
        productName.setText(product.getName());
        Picasso.get().load(product.getImage()).into(productImage);


        RecyclerView detailRecyclerView = findViewById(R.id.product_detail_recycler_view);
        DetailAdapter detailAdapter = new DetailAdapter(product.getDetails());
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        detailRecyclerView.setLayoutManager(layoutManagerProduct);
        detailRecyclerView.setAdapter(detailAdapter);


        RecyclerView adCampaignRecyclerView = findViewById(R.id.product_ad_recycler_view);
        AdCampaignProductAdapter campaignProductAdapter = new AdCampaignProductAdapter(product.getAdcampaigns());
        RecyclerView.LayoutManager layoutManagerAdCampaign = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        adCampaignRecyclerView.setLayoutManager(layoutManagerAdCampaign);
        adCampaignRecyclerView.setAdapter(campaignProductAdapter);
        campaignProductAdapter.setOnItemClickListener(new AdCampaignProductAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String EXTRA_MESSAGE = "GivingAdCampaign";
                Intent intent = new Intent(ProductPage.this, AdPage.class);
                AdCampaign adCampaign = product.getAdcampaigns().get(position);
                intent.putExtra(EXTRA_MESSAGE, adCampaign);
                intent.putExtra("GivingEstablishment", "View all Establishments");

                startActivity(intent);
            }
        });


    }
}