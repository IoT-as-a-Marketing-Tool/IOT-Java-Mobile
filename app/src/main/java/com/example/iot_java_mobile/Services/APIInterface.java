package com.example.iot_java_mobile.Services;

import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("api/customers/1/brands/")
    Call<List<Brand>> getBrands();


    @GET("api/customers/1/products/")
    Call<List<Product>> getProducts();

    @GET("api/customers/1/products/{id}/")
    Call<Product> getProduct(@Path("id") int id);

    @GET("api/customers/1/brands/{id}/")
    Call<Brand> getBrand(@Path("id") int id);

    @GET("api/customers/1/beacons/{uuid}/")
    Call<EstProduct> getEstProduct(@Path("uuid") String uuid);

    @GET("api/customers/1/establishments/")
    Call<List<Establishment>> getEstablishments();

    @GET("api/customers/1/establishments/{id}/")
    Call <Establishment> getEstablishment(@Path("id") int id);

}
