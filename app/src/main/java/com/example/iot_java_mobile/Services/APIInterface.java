package com.example.iot_java_mobile.Services;

import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.LoginUser;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.Profile;
import com.example.iot_java_mobile.Domain.User;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {
    @GET("api/customers/1/brands/")
    Call<List<Brand>> getBrands();


    @GET("api/customers/1/products/")
    Call<List<Product>> getProducts();

    @GET("api/customers/1/establishments/")
    Call<List<Establishment>> getEstablishments();

    @POST("api/users/register")
    Call<Object> createUser(@Body User user

    );
    @POST("api/users/login")
    Call<Object> loginUser(@Body LoginUser user

    );
    @POST("api/customers/")
    Call<Object> createProfile(@Body Profile profile, @Header("Authorization") String auth

    );
    @GET("api/customers/current/")
    Call<Object> getCustomer(@Header("Authorization") String auth);



}
