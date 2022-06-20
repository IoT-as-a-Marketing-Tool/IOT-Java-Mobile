package com.example.iot_java_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Adaptor.SeeAllBrandAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeeAllBrandFragment extends Fragment {


    public SeeAllBrandFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_all_brands, container, false);
        Bundle bundle = this.getArguments();
        List<Brand> brandList = (List<Brand>) bundle.getSerializable("AllBrandsAttached");


        RecyclerView brandRecyclerView =v.findViewById(R.id.all_brand_recycler_view);
        SeeAllBrandAdapter seeAllBrandAdapter = new SeeAllBrandAdapter(brandList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        brandRecyclerView.setLayoutManager(layoutManagerProduct);
        brandRecyclerView.setAdapter(seeAllBrandAdapter);
        seeAllBrandAdapter.setOnItemClickListener(new SeeAllBrandAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Fragment brandFragment = new BrandFragment();
                Brand brand = brandList.get(position);
                Log.e("Don", "onItemClick: brand clicked "+brand );
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("BrandAttached", brand);
                brandFragment.setArguments(bundle1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, brandFragment).commit();

            }
        });
        Button backBtn = v.findViewById(R.id.back_button_see_all_brands);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SessionManager sessionManager = new SessionManager(getContext());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, new HomeFragment(sessionManager)).commit();

                    }}
        );

        EditText editText = v.findViewById(R.id.search_brand);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                APIInterface apiInterface = APIClient.getRetrofitClient();
                String val = editable.toString();
//                Log.e("Don", "afterTextChanged: "+ val );
                apiInterface.getBrandsFiltered(MainActivity.custID, val).enqueue(
                        new Callback<List<Brand>>() {
                            @Override
                            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
//                                Log.e("Tag", "requessttt== "+ call.request().url().toString() );
                                if(response.isSuccessful() && response.body()!=null){
//                                    Log.e("Don", "onResponse: not null" );
                                    brandList.clear();
                                    seeAllBrandAdapter.notifyDataSetChanged();

                                    brandList.addAll(response.body());
//                                    Log.e("Don", "onResponse: productList "+productList );
//                                    Log.e("Don", "onResponse: response " +response.body() );
                                    seeAllBrandAdapter.notifyDataSetChanged();
//                                    Log.e("Don", "onResponse: notirydataset changed called" );
                                }
                                Log.e("Don", "onResponse: "+val );
                            }

                            @Override
                            public void onFailure(Call<List<Brand>> call, Throwable t) {
                                Log.e("Don", "onFailure: "+t.getMessage() );
                            }
                        }
                );
            }
        });


        return v;



    }
}