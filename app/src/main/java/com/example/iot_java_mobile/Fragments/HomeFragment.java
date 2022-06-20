package com.example.iot_java_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Adaptor.BrandsAdapter;
import com.example.iot_java_mobile.Adaptor.RecentAdHomeAdapter;
import com.example.iot_java_mobile.Adaptor.ProductsHomeAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.Domain.User;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    SessionManager sessionManager;
    public HomeFragment() {
        sessionManager = new SessionManager(getContext());
        // Required empty public constructor
    }
    public HomeFragment(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        // Required empty public constructor
    }

    private ImageSlider imageSlider;
    private TextView homeBrand;
    private TextView homeAd;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewHomeBrands;
    private RecyclerView recyclerViewHomeProducts;
    private RecyclerView recyclerViewHomeEstablishments;
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Brand> brandList;
    private List<Product> productList;
    private List<AdCampaign> recentCampaignList;

    BrandsAdapter brandAdapter;
    ProductsHomeAdapter productAdapter;
    RecentAdHomeAdapter recentAdHomeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics= FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
//


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        brandList = new ArrayList<Brand>();
        productList = new ArrayList<Product>();
        recentCampaignList = new ArrayList<AdCampaign>();
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = v.findViewById(R.id.carousel);

        TextView username = v.findViewById(R.id.home_username);
        User user= sessionManager.getUserDetails();
        username.setText("Hello, "+ user.getUsername());

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://thumbs.dreamstime.com/z/mango-juice-ads-liquid-hand-banner-grabbing-fruit-effect-blue-sky-background-d-illustration-152914246.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.sunchipsethiopia.net/img/photo_2021-04-27_15-48-07.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.logogenie.net/images/articles/brands-of-the-world-coca-cola.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://world.openfoodfacts.org/images/products/878/456/290/8364/front_fr.3.full.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        brandAdapter = new BrandsAdapter(brandList);
        recyclerViewHomeBrands = v.findViewById(R.id.home_brand_list_recycler_view);
        RecyclerView.LayoutManager layoutManagerBrand = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        recyclerViewHomeBrands.setLayoutManager(layoutManagerBrand);
        recyclerViewHomeBrands.setAdapter(brandAdapter);
        brandAdapter.setOnItemClickListener(new BrandsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String EXTRA_MESSAGE = "BrandAttached";
//                Intent intent = new Intent(v, BrandPage.class);
                Brand brand = brandList.get(position);
                Bundle firebundle = new Bundle();
                firebundle.putString(FirebaseAnalytics.Param.ITEM_ID, "brand"+brand.getId());
                firebundle.putString(FirebaseAnalytics.Param.ITEM_NAME, brand.getName());
                firebundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "brand");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, firebundle);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_MESSAGE, brand);

                Fragment brandFragment = new BrandFragment();
                brandFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,brandFragment).commit();
//                startActivity(intent);
            }
        });
        TextView brandSeeAllTV = v.findViewById(R.id.home_brand_see_all);
        brandSeeAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("AllBrandsAttached", (Serializable) brandList);
                Fragment brandFragment = new SeeAllBrandFragment();
                brandFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,brandFragment).commit();
//
            }
        });

        productAdapter = new ProductsHomeAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        recyclerViewHomeProducts = v.findViewById(R.id.home_product_list_recycler_view);
        recyclerViewHomeProducts.setLayoutManager(layoutManagerProduct);
        recyclerViewHomeProducts.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductsHomeAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                APIInterface apiInterface = APIClient.getRetrofitClient();
                apiInterface.getProduct(productList.get(position).getId(), MainActivity.custID).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            String EXTRA_MESSAGE = "GivingProduct";
//                            Intent intent = new Intent(v, ProductPage.class);

//                            Log.e("Don", "Adcampaigns    : "+ response.body());
//                            intent.putExtra(EXTRA_MESSAGE, product);
//                            startActivity(intent);
                            Fragment productFragment = new ProductFragment();
                            Product product = response.body();
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable(EXTRA_MESSAGE, product);
                            Log.e("Don", "Adcampaigns    : "+ response.body());
                            productFragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, productFragment).commit();

                        }


                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                        Toast.makeText(getContext(), "Sorry this is not working right now, Try again later ",Toast.LENGTH_SHORT).show();
                        Log.e("Don", t.getMessage());
                    }
                });

            }
        });
        TextView productSeeAllTV = v.findViewById(R.id.home_product_see_all);
        productSeeAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("AllProductsAttached", (Serializable) productList);
                Fragment productFragment = new SeeAllProductFragment();
                productFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,productFragment).commit();
//
            }
        });


        recentAdHomeAdapter = new RecentAdHomeAdapter(recentCampaignList);
        RecyclerView.LayoutManager layoutManagerEst = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        recyclerViewHomeEstablishments = v.findViewById(R.id.home_est_list_recycler_view);
        recyclerViewHomeEstablishments.setLayoutManager(layoutManagerEst);
        recyclerViewHomeEstablishments.setAdapter(recentAdHomeAdapter);
        recentAdHomeAdapter.setOnItemClickListener(
                new RecentAdHomeAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        String EXTRA_MESSAGE = "GivingCampaignList";

                        Intent intent = new Intent(getContext(), AdsPage.class);
                        List<AdCampaign> adCampaignList = new ArrayList<>();
                        adCampaignList.add(recentCampaignList.get(position));
                        intent.putExtra(EXTRA_MESSAGE, (Serializable) adCampaignList);
                        List<Establishment> establishmentList = new ArrayList<>();
//                for (int i = 0; i < adCampaignList.size(); i++){
//                    establishmentNameList.add("View all Establishments");
//                }
                        intent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);

                        startActivity(intent);
                    }
                }
        );
        TextView recentAdsSeeAllTV = v.findViewById(R.id.home_ads_see_all);
        recentAdsSeeAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EXTRA_MESSAGE = "GivingCampaignList";
                Intent intent = new Intent(getContext(), AdsPage.class);
                List<AdCampaign> adCampaignList = recentCampaignList;
                intent.putExtra(EXTRA_MESSAGE, (Serializable) adCampaignList);
                List<Establishment> establishmentList = new ArrayList<>();
//                for (int i = 0; i < adCampaignList.size(); i++){
//                    establishmentNameList.add("View all Establishments");
//                }
                intent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);

                startActivity(intent);
            }
        });

        fetchData();
        
        return v;
    }


    private void fetchData() {
        APIInterface apiInterface = APIClient.getRetrofitClient();
        apiInterface.getBrands(MainActivity.custID).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if(response.isSuccessful() && response.body()!= null){
//                  Brand.Color c = apiClient.gson.fromJson(response.body(), Brand.Color.class);
                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
//                  Log.e("Don", "SUCCESSSSS "+ response.body().get(0).toString());


                    Log.e("Don", "SUCCESSSSS " + response.body());
                    brandList.addAll(response.body());
                    brandAdapter.notifyDataSetChanged();
                    Log.e("Don", "SUCCESSSSS " + brandList.get(0).getColors());
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry this is not working right now, Try again later ",Toast.LENGTH_SHORT).show();
                Log.e("Don", t.getMessage());
            }
        });

        apiInterface.getProducts(MainActivity.custID).enqueue(
                new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            Log.e("Don", "SUCCESSSSS " + response.body());
                            productList.addAll(response.body());
                            productAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getContext(), "Sorry this is not working right now, Try again later ",Toast.LENGTH_SHORT).show();
                        Log.e("Don", t.getMessage());
                    }
                }
        );
//        apiInterface.getEstablishments().enqueue(
//                new Callback<List<Establishment>>() {
//                    @Override
//                    public void onResponse(Call<List<Establishment>> call, Response<List<Establishment>> response) {
//                        if(response.isSuccessful() && response.body()!= null){
//                            Log.e("Don", "SUCCESSSSS " + response.body());
//                            recentCampaignList.addAll(response.body());
//                            establishmentAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Establishment>> call, Throwable t) {
//                        Toast.makeText(v, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
//                        Log.e("Don", t.getMessage());
//                    }
//                }
//        );

        String token= "Bearer "+ sessionManager.getAuthToken();
        apiInterface.getRecentAds(MainActivity.custID, token).enqueue(new Callback<List<AdCampaign>>() {
            @Override
            public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {
                if(response.isSuccessful() && response.body()!= null){
                    recentCampaignList.addAll(response.body());
                    recentAdHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<AdCampaign>> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry this is not working right now, Try again later ",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.option_bar_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item:
                sessionManager.logoutUser();
                Log.e("Don", "onOptionsItemSelected: touchceeeeedddd" );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}