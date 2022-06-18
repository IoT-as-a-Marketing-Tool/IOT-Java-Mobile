package com.example.iot_java_mobile.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Adaptor.ProductsBrandAdapter;
import com.example.iot_java_mobile.Adaptor.SeeAllBrandAdapter;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Product;
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

        return v;



    }
}