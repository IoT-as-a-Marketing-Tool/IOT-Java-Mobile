package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;

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


        fetchData();

        brandAdapter = new BrandsAdapter(brandList);
        recyclerViewHomeBrands = findViewById(R.id.home_brand_list_recycler_view);
        RecyclerView.LayoutManager layoutManagerBrand = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeBrands.setLayoutManager(layoutManagerBrand);
        recyclerViewHomeBrands.setAdapter(brandAdapter);

        productAdapter = new ProductsHomeAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeProducts = findViewById(R.id.home_product_list_recycler_view);
        recyclerViewHomeProducts.setLayoutManager(layoutManagerProduct);
        recyclerViewHomeProducts.setAdapter(productAdapter);


        establishmentAdapter = new EstablishmentsHomeAdapter(establishmentList);
        RecyclerView.LayoutManager layoutManagerEst = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerViewHomeEstablishments = findViewById(R.id.home_est_list_recycler_view);
        recyclerViewHomeEstablishments.setLayoutManager(layoutManagerEst);
        recyclerViewHomeEstablishments.setAdapter(establishmentAdapter);

//        recyclerViewBrands();
    }

    private void fetchData() {
        APIClient.getRetrofitClient().getBrands().enqueue(new Callback<List<Brand>>() {
          @Override
          public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
              if(response.isSuccessful() && response.body()!= null){
                  Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
                  Log.e("Don", "SUCCESSSSS ");
                  Log.e("Don", "SUCCESSSSS ");

                  Log.e("Don", "SUCCESSSSS " + response.body());
                  brandList.addAll(response.body());
                  brandAdapter.notifyDataSetChanged();
              }
          }

          @Override
          public void onFailure(Call<List<Brand>> call, Throwable t) {
              Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
          }
      });

        APIClient.getRetrofitClient().getProducts().enqueue(
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
        APIClient.getRetrofitClient().getEstablishments().enqueue(
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