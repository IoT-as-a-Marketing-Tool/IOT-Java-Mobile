package com.example.iot_java_mobile.Services;

import android.util.Log;

import com.example.iot_java_mobile.Domain.Brand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

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

//            if (jsonArray.size()==0){
//                Log.e("Don", "deserialize: this is null");
//            }else{
//                Log.e("Don", "deserialize: not null");
//            }
//            for (JsonElement itemsJsonElement : jsonArray) {
//                final JsonObject itemJsonObject = itemsJsonElement.getAsJsonObject();
//                if (itemJsonObject.get("desription")==null){
//                    Log.e("Don", "deserialize: I WAS WRONG");
//                }else{
//                    Log.e("Don", "deserialize: okay" );
//                }
//                final JsonObject colors = itemJsonObject.get("colors").getAsJsonObject();
//

//                final int amount = itemJsonObject.get("amount").getAsInteger();


//            }
//            return null;
//            final JsonElement colors = jsonArray.get("description");
//            if (colors==null){
//                Log.e("Don", "deserialize: this is null");
//            }else{
//                Log.e("Don", "deserialize: not null");
//            }
//            final String p_color = ((JsonObject)colors).get("primaryColor").getAsString();
//            final String s_color = ((JsonObject)colors).get("secondaryColor").getAsString();
//
//
//            return new Brand.Color(p_color,s_color);
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
