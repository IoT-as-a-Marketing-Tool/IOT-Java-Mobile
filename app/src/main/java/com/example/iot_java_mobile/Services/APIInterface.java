package com.example.iot_java_mobile.Services;

import com.example.iot_java_mobile.Adaptor.BrandsAdapter;
import com.example.iot_java_mobile.Domain.Brands;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/customers/1/brands/")
    Call<Brands> getBrands();


}
