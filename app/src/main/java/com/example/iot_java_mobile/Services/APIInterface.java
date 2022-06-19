package com.example.iot_java_mobile.Services;

import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.LoginUser;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.Profile;
import com.example.iot_java_mobile.Domain.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("api/customers/{cust_id}/brands/")
    Call<List<Brand>> getBrands(@Path("cust_id") int cust_id);


    @GET("api/customers/{cust_id}/products/")
    Call<List<Product>> getProducts(@Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/products/{id}/")
    Call<Product> getProduct(@Path("id") int id,@Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/brands/{id}/")
    Call<Brand> getBrand(@Path("id") int id, @Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/beacons/{uuid}/")
    Call<EstProduct> getEstProduct(@Path("uuid") String uuid, @Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/establishments/")
    Call<List<Establishment>> getEstablishments(@Path("cust_id") int cust_id);

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

    /**
     *
     * path('<int:pk>/recent_campaigns/', RecentCampaignListAPIView.as_view()),
     *     path('<int:pk>/favorite_brands/', FavoriteBrandListAPIView.as_view()),
     *     path('<int:pk>/favorite_brand/<int:brand_id>/', FavoriteBrandUpdateView.as_view()),
     *     path('<int:pk>/unfavorite_brand/<int:brand_id>/', UnFavoriteBrandUpdateView.as_view()),
     *     path('<int:pk>/notifications/', NotificationListCreateAPIView.as_view()),
     *     path('<int:pk>/notifications/<int:campaign_id>/', NotificationDeleteAPIView.as_view()),
     * */
    @GET("api/customers/{cust_id}/recent_campaigns/")
    Call <List<AdCampaign>> getRecentAds(@Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/favorite_brands/")
    Call<List<Brand>> getFavoriteBrands(@Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/favorite_brand/{id}/")
    Call<List<Brand>> favoriteBrand(@Path("id") int id, @Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/unfavorite_brand/{id}/")
    Call<List<Brand>> unfavoriteBrand(@Path("id") int id,@Path("cust_id") int cust_id);

    @GET("api/customers/{cust_id}/notifications/")
    Call<List<AdCampaign>> getNotifications(@Path("cust_id") int cust_id);

    @POST("api/customers/{cust_id}/notifications/")
    Call<List<AdCampaign>> createNotifications(@Path("cust_id") int cust_id, @Body HashMap<String, List> campaigns);

    @GET("api/customers/{cust_id}/notifications/{id}/")
    Call <List<AdCampaign>> deleteNotification(@Path("id") int id, @Path("cust_id") int cust_id);



}
