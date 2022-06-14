package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.iot_java_mobile.Adaptor.AdsAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.R;

import java.util.List;

public class AdsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String EXTRA_MESSAGE = "GivingEstProducts";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_page);

        Intent intent = getIntent();
        List<EstProduct> estProductList = (List<EstProduct>) intent.getSerializableExtra(EXTRA_MESSAGE);

        Log.e("Don", "onCreate: estProducts == "+ estProductList );
        AdsAdapter adsAdapter = new AdsAdapter(estProductList);

        RecyclerView recyclerView = findViewById(R.id.ads_recycler_view);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerProduct);
        recyclerView.setAdapter(adsAdapter);










    }
}