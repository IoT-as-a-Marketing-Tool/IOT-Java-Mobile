package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iot_java_mobile.Adaptor.BrandsAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsHomeAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BrandPage extends AppCompatActivity {
    List<Product> productList = new ArrayList<Product>();
    private ProductsBrandAdapter productAdapter;

    //    productAdapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_page);
        String EXTRA_MESSAGE = "BrandAttached";
        Intent intent = getIntent();
        Brand brand = (Brand) intent.getSerializableExtra(EXTRA_MESSAGE);
        productList = brand.getProducts();
        TextView brandNameTextView = findViewById(R.id.brand_name);
        ImageView brandImageView = findViewById(R.id.brand_image);
        TextView brandDescTextView = findViewById(R.id.brand_description);
        RecyclerView productRecyclerView = findViewById(R.id.brand_product_list_recycler_view);

        brandNameTextView.setText(brand.getName());
        brandDescTextView.setText(brand.getDescription());
        Picasso.get().load(brand.getLogo()).into(brandImageView);



        productAdapter = new ProductsBrandAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        productRecyclerView = findViewById(R.id.brand_product_list_recycler_view);
        productRecyclerView.setLayoutManager(layoutManagerProduct);
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductsBrandAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String EXTRA_MESSAGE = "FromBrandPageToProductPage";
                Intent intent = new Intent(BrandPage.this, ProductPage.class);
                Product product = productList.get(position);
                intent.putExtra(EXTRA_MESSAGE +"GivingProduct", product);
                intent.putExtra(EXTRA_MESSAGE +"GivingBrand", brand);
                startActivity(intent);
            }
        });



    }
}