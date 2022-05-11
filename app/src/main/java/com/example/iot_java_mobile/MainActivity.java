package com.example.iot_java_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSlider.findViewById(R.id.carousel);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add( new SlideModel("https://thumbs.dreamstime.com/z/mango-juice-ads-liquid-hand-banner-grabbing-fruit-effect-blue-sky-background-d-illustration-152914246.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://www.sunchipsethiopia.net/img/photo_2021-04-27_15-48-07.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://www.logogenie.net/images/articles/brands-of-the-world-coca-cola.jpg", ScaleTypes.FIT));
        slideModels.add( new SlideModel("https://world.openfoodfacts.org/images/products/878/456/290/8364/front_fr.3.full.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}