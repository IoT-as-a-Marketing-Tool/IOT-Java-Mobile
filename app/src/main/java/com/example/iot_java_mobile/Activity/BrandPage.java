//package com.example.iot_java_mobile.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.iot_java_mobile.Adaptor.BrandsAdapter;
//import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
//import com.example.iot_java_mobile.Adaptor.ProductsHomeAdapter;
//import com.example.iot_java_mobile.Domain.Brand;
//import com.example.iot_java_mobile.Domain.Establishment;
//import com.example.iot_java_mobile.Domain.Product;
//import com.example.iot_java_mobile.R;
//import com.squareup.picasso.Picasso;
//import com.example.iot_java_mobile.Services.APIClient;
//import com.example.iot_java_mobile.Services.APIInterface;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class BrandPage extends AppCompatActivity {
//    List<Product> productList = new ArrayList<Product>();
//    private ProductsBrandAdapter productAdapter;
//
//    //    productAdapter
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_brand_page);
//        String EXTRA_MESSAGE = "BrandAttached";
//        Intent intent = getIntent();
//        Brand brand = (Brand) intent.getSerializableExtra(EXTRA_MESSAGE);
//        productList = brand.getProducts();
//        TextView brandNameTextView = findViewById(R.id.brand_name);
//        ImageView brandImageView = findViewById(R.id.brand_image);
//        TextView brandDescTextView = findViewById(R.id.brand_description);
//        RecyclerView productRecyclerView = findViewById(R.id.brand_product_list_recycler_view);
//
//        brandNameTextView.setText(brand.getName());
//        brandDescTextView.setText(brand.getDescription());
//        Picasso.get().load(brand.getLogo()).into(brandImageView);
//
//        Button favBtn = findViewById(R.id.brand_fav_btn);
//        favBtn.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        favBtn.setBackgroundResource(R.drawable.ic_baseline_home_24);
//                        Intent intent = new Intent(BrandPage.this, FavoritesPage.class);
////                        intent.putExtra("GivingListOf")
//                        startActivity(intent);
//                    }
//                }
//        );
//
//
//        productAdapter = new ProductsBrandAdapter(productList);
//        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
//        productRecyclerView.setLayoutManager(layoutManagerProduct);
//        productRecyclerView.setAdapter(productAdapter);
//        productAdapter.setOnItemClickListener(new ProductsBrandAdapter.ClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.e("Don", "onItemClick: show id "+ productList.get(position).getId()  );
//                APIInterface apiInterface = APIClient.getRetrofitClient();
//                apiInterface.getProduct(productList.get(position).getId(),MainActivity.custID).enqueue(new Callback<Product>() {
//                    @Override
//                    public void onResponse(Call<Product> call, Response<Product> response) {
//                        if(response.isSuccessful() && response.body()!= null){
//                            String EXTRA_MESSAGE = "GivingProduct";
//                            Intent intent = new Intent(BrandPage.this, ProductPage.class);
//                            Product product = response.body();
//                            Log.e("Don", "Adcampaigns    : "+ response.body());
//                            intent.putExtra(EXTRA_MESSAGE, product);
//                            startActivity(intent);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Product> call, Throwable t) {
//
//                        Toast.makeText(BrandPage.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
////                intent.putExtra(EXTRA_MESSAGE +"GivingBrand", brand);
//            }
//        });
//
//
//
//    }
//}