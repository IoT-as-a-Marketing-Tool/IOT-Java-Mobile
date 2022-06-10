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
import com.example.iot_java_mobile.Adaptor.EstablishmentsHomeAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsHomeAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.Domain.User;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    private RecyclerView recyclerViewHomeProducts;
    private RecyclerView recyclerViewHomeEstablishments;

    private List<Brand> brandList = new ArrayList<Brand>() ;
    private List<Product> productList = new ArrayList<Product>() ;
    private List<Establishment> establishmentList = new ArrayList<Establishment>() ;

    BrandsAdapter brandAdapter;
    ProductsHomeAdapter productAdapter;
    EstablishmentsHomeAdapter establishmentAdapter;
    SessionManager sessionManager;



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
        homeBrand = findViewById(R.id.logoutButton);
        homeBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();

            }
        });
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

        sessionManager=  new SessionManager(getApplicationContext());

        fetchData();
        TextView username = findViewById(R.id.home_username);
        User user= sessionManager.getUserDetails();
        username.setText("Hello, "+ user.getUsername());
        brandAdapter = new BrandsAdapter(brandList);
        recyclerViewHomeBrands = findViewById(R.id.home_brand_list_recycler_view);
        RecyclerView.LayoutManager layoutManagerBrand = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeBrands.setLayoutManager(layoutManagerBrand);
        recyclerViewHomeBrands.setAdapter(brandAdapter);
        brandAdapter.setOnItemClickListener(new BrandsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String EXTRA_MESSAGE = "BrandAttached";
                Intent intent = new Intent(MainActivity.this, BrandPage.class);
                Brand brand = brandList.get(position);
                intent.putExtra(EXTRA_MESSAGE, brand);
                startActivity(intent);
            }
        });

        productAdapter = new ProductsHomeAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeProducts = findViewById(R.id.home_product_list_recycler_view);
        recyclerViewHomeProducts.setLayoutManager(layoutManagerProduct);
        recyclerViewHomeProducts.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductsHomeAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                APIInterface apiInterface = APIClient.getRetrofitClient(Product.Details.class, new APIClient.ProductDetailDeserializer());
                apiInterface.getProduct(productList.get(position).getId()).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            String EXTRA_MESSAGE = "GivingProduct";
                            Intent intent = new Intent(MainActivity.this, ProductPage.class);
                            Product product = response.body();
                            Log.e("Don", "Adcampaigns    : "+ response.body());
                            intent.putExtra(EXTRA_MESSAGE, product);
                            startActivity(intent);
                        }


                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                        Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        establishmentAdapter = new EstablishmentsHomeAdapter(establishmentList);
        RecyclerView.LayoutManager layoutManagerEst = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeEstablishments = findViewById(R.id.home_est_list_recycler_view);
        recyclerViewHomeEstablishments.setLayoutManager(layoutManagerEst);
        recyclerViewHomeEstablishments.setAdapter(establishmentAdapter);

//        recyclerViewBrands();
    }

    private void fetchData() {
        APIInterface apiInterface = APIClient.getRetrofitClient(Product.Details.class, new APIClient.ProductDetailDeserializer());
        apiInterface.getBrands().enqueue(new Callback<List<Brand>>() {
          @Override
          public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
              if(response.isSuccessful() && response.body()!= null){
//                  Brand.Color c = apiClient.gson.fromJson(response.body(), Brand.Color.class);
                  Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
//                  Log.e("Don", "SUCCESSSSS "+ response.body().get(0).toString());


                  Log.e("Don", "SUCCESSSSS " + response.body());
                  brandList.addAll(response.body());
                  brandAdapter.notifyDataSetChanged();
                  Log.e("Don", "SUCCESSSSS " + brandList.get(0).getColors());
              }
          }

          @Override
          public void onFailure(Call<List<Brand>> call, Throwable t) {
              Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
          }
      });

        apiInterface.getProducts().enqueue(
                new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            Log.e("Don", "SUCCESSSSS " + response.body());
                            productList.addAll(response.body());
                            productAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );
        apiInterface.getEstablishments().enqueue(
                new Callback<List<Establishment>>() {
                    @Override
                    public void onResponse(Call<List<Establishment>> call, Response<List<Establishment>> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            Log.e("Don", "SUCCESSSSS " + response.body());
                            establishmentList.addAll(response.body());
                            establishmentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Establishment>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );

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