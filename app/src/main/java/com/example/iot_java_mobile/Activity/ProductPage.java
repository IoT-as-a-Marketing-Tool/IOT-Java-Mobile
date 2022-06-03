package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iot_java_mobile.Adaptor.DetailAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

public class ProductPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: change so that it can only accept product object
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        String EXTRA_MESSAGE = "FromBrandPageToProductPage";
        Intent intent = getIntent();
        Brand brand = (Brand) intent.getSerializableExtra(EXTRA_MESSAGE+"GivingBrand");
        Product product = (Product) intent.getSerializableExtra(EXTRA_MESSAGE+"GivingProduct");

        TextView brandName = findViewById(R.id.product_brand_name);
        TextView productName = findViewById(R.id.product_name);
        ImageView productImage = findViewById(R.id.product_image);


        brandName.setText(brand.getName());
        productName.setText(product.getName());
        Picasso.get().load(product.getImage()).into(productImage);


        RecyclerView detailRecyclerView = findViewById(R.id.product_detail_recycler_view);
        DetailAdapter detailAdapter = new DetailAdapter(product.getDetails());
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        detailRecyclerView.setLayoutManager(layoutManagerProduct);
        detailRecyclerView.setAdapter(detailAdapter);





    }
}