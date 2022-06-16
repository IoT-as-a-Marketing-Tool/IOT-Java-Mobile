package com.example.iot_java_mobile.Services;

import android.util.Log;

import com.example.iot_java_mobile.Domain.AdItem;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
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
            Brand.Color c = null;
            if (jsonObject.has("primaryColor") && jsonObject.has("secondaryColor")){
                final String p_color = jsonObject.get("primaryColor").getAsString();
                final String s_color = jsonObject.get("secondaryColor").getAsString();
                c = new Brand.Color(p_color, s_color);
            }

            return c;


//
        }
    }
    public static class LocationDeserializer implements JsonDeserializer<Establishment.Location>
    {

        @Override
        public Establishment.Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            Establishment.Location l = null;
            if(jsonObject.has("latitude") && jsonObject.has("longititude")){
                final double latitude = jsonObject.get("latitude").getAsDouble();
                final double longitude = jsonObject.get("longititude").getAsDouble();
                l = new Establishment.Location(latitude, longitude);
            }

            return l;


//
        }
    }
    public static class ProductDetailDeserializer implements JsonDeserializer<Product.Details>{
        Gson gson;

        public ProductDetailDeserializer(Gson gson) {
            this.gson = gson;
        }
        @Override
        public Product.Details deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonElement jsonObject = json.getAsJsonObject();
            HashMap<String, Object> detail = gson.fromJson(jsonObject, HashMap.class);
            Log.e("Don", "detail deserialize: we're herreeeeeeeeeeeeeeee" );
            Log.e("Don", "detail deserialized: "+ detail );
            return new Product.Details(detail);
        }
    }
    public  static class AdItemStyleDeserializer implements JsonDeserializer<AdItem.Style>{
        Gson gson;

        public AdItemStyleDeserializer(Gson gson) {
            this.gson = gson;
        }

        @Override
        public AdItem.Style deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonElement jsonObject = json.getAsJsonObject();
            HashMap<String, Object> style = gson.fromJson(jsonObject, HashMap.class);
            Log.e("Don", "style deserialize: we're in STYLE herreeeeeeeeeeeeeeee" );

            return new AdItem.Style(style);
        }
    }

    private static Converter.Factory createGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = new Gson();
        gsonBuilder.registerTypeAdapter(AdItem.Style.class, new AdItemStyleDeserializer(gson));
        gsonBuilder.registerTypeAdapter(Product.Details.class, new ProductDetailDeserializer(gson));
        gsonBuilder.registerTypeAdapter(Establishment.Location.class, new LocationDeserializer());
        gsonBuilder.registerTypeAdapter(Brand.Color.class, new ColorDeserializer());


        gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }


    public static APIInterface getRetrofitClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(createGsonConverter())
                    .build();
        }
        return retrofit.create(APIInterface.class);
    }
}
