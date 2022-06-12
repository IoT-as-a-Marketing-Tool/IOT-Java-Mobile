package com.example.iot_java_mobile.Services;

import android.util.Log;

import com.example.iot_java_mobile.Domain.AdItem;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static String BASE_URL = "https://iot-api-g22.herokuapp.com/";
    public static Retrofit retrofit = null;
    public static class ColorDeserializer implements JsonDeserializer<Brand.Color>
    {

        @Override
        public Brand.Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final String p_color = jsonObject.get("primaryColor").getAsString();
            final String s_color = jsonObject.get("secondaryColor").getAsString();
            Brand.Color c = new Brand.Color(p_color, s_color);
            return c;


//
        }
    }
    public static class ProductDetailDeserializer implements JsonDeserializer<Product.Details>{

        @Override
        public Product.Details deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonElement jsonObject = json.getAsJsonObject();
            HashMap<String, Object> detail = new Gson().fromJson(jsonObject, HashMap.class);
            Log.e("Don", "deserialize: we're herreeeeeeeeeeeeeeee" );
            return new Product.Details(detail);
        }
    }
    public  static class AdItemStyleDeserializer implements JsonDeserializer<AdItem.Style>{

        @Override
        public AdItem.Style deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonElement jsonObject = json.getAsJsonObject();
            AdItem.Style style = new Gson().fromJson(jsonObject, AdItem.Style.class);
            Log.e("Don", "deserialize: we're in STYLE herreeeeeeeeeeeeeeee" );
            return style;
        }
    }

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }


    public static APIInterface getRetrofitClient(Type type, Object typeAdapter){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(createGsonConverter(type, typeAdapter))
                    .build();
        }
        return retrofit.create(APIInterface.class);
    }
    public static APIInterface authClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIInterface.class);
    }
}
