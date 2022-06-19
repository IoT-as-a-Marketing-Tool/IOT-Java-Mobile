package com.example.iot_java_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Customer;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrandFragment extends Fragment {
    List<Product> productList = new ArrayList<Product>();
    private ProductsBrandAdapter productAdapter;
    SessionManager sessionManager;
    public BrandFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_brand, container, false);
        String EXTRA_MESSAGE = "BrandAttached";
        Bundle bundle = this.getArguments();
        Brand brand = (Brand) bundle.getSerializable(EXTRA_MESSAGE);
        productList = brand.getProducts();
        sessionManager = new SessionManager(getContext());


        TextView brandNameTextView =v.findViewById(R.id.brand_name);
        ImageView brandImageView =v.findViewById(R.id.brand_image);
        TextView brandDescTextView =v.findViewById(R.id.brand_description);
        RecyclerView productRecyclerView =v.findViewById(R.id.brand_product_list_recycler_view);

        brandNameTextView.setText(brand.getName());
        brandDescTextView.setText(brand.getDescription());
        Picasso.get().load(brand.getLogo()).into(brandImageView);
        APIInterface apiInterface = APIClient.getRetrofitClient();
        Button favBtn =v.findViewById(R.id.brand_fav_btn);
        Button backBtn= v.findViewById(R.id.back_button_brand);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }}
        );
        favBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isFavoritedBrand(brand)){
                            // unFavorite it
                            Toast.makeText(getContext(), "UnFavoriting", Toast.LENGTH_LONG);
                            Customer customer = null;
                            try {
                                customer = sessionManager.getCustomerDetails();
                                if(customer.getFavorites()!=null){
                                    customer.getFavorites().remove(brand.getId());
                                }else{
                                    customer.setFavorites( new ArrayList<>());
                                }

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                            apiInterface.unfavoriteBrand(brand.getId(), MainActivity.custID).enqueue(new Callback<List<Brand>>() {
                                @Override
                                public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                                    if(response.isSuccessful() && response.body()!= null) {
                                        Log.e("Don", "onResponse: UNFAVORITED");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Brand>> call, Throwable t) {
                                    Log.e("Don", "onResponse: not successful unfavorite");
                                }
                            });

                        }else{
                            //favorite
                            Toast.makeText(getContext(), "Favoriting", Toast.LENGTH_LONG);

                            apiInterface.favoriteBrand(brand.getId(), MainActivity.custID).enqueue(new Callback<List<Brand>>() {
                                @Override
                                public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                                    if(response.isSuccessful() && response.body()!= null) {
                                        Log.e("Don", "onResponse: FAVORITED");
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Brand>> call, Throwable t) {
                                    Log.e("Don", "onResponse: not successful favorite");
                                }
                            });

                            Customer customer = null;
                            try {
                                customer = sessionManager.getCustomerDetails();
                                if(customer.getFavorites()==null){
                                    customer.setFavorites( new ArrayList<>());

                                }
                                customer.getFavorites().add(brand.getId());
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }



                        }
//                        favBtn.getBackground() ==
//                        favBtn.setBackgroundResource(R.drawable.ic_baseline_home_24);
                        Fragment favoriteFragment = new FavoritesFragment();
                        Bundle bundle1 = new Bundle();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, favoriteFragment).commit();

//                        Intent intent = new Intent(BrandPage.this, FavoritesPage.class);
//                        intent.putExtra("GivingListOf")
//                        startActivity(intent);
                    }
                }
        );


        productAdapter = new ProductsBrandAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        productRecyclerView.setLayoutManager(layoutManagerProduct);
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductsBrandAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.e("Don", "onItemClick: show id "+ productList.get(position).getId()  );
                APIInterface apiInterface = APIClient.getRetrofitClient();
                apiInterface.getProduct(productList.get(position).getId(), MainActivity.custID).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            String EXTRA_MESSAGE = "GivingProduct";
//                            Intent intent = new Intent(BrandPage.this, ProductPage.class);
                            Fragment productFragment = new ProductFragment();
                            Product product = response.body();
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable(EXTRA_MESSAGE, product);
                            Log.e("Don", "Adcampaigns    : "+ response.body());
                            productFragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, productFragment).commit();

//                            intent.putExtra(EXTRA_MESSAGE, product);
//                            startActivity(intent);
                        }


                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                        Toast.makeText(getContext(), "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


//                intent.putExtra(EXTRA_MESSAGE +"GivingBrand", brand);
            }
        });
        return v;



    }

    private boolean isFavoritedBrand(Brand brand) {

        try {
            Customer c = sessionManager.getCustomerDetails();
            if (c.getFavorites() != null && c.getFavorites().contains(brand))
                return true;
            return false;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;

    }
}