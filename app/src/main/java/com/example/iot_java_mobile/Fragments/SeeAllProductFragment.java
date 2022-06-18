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

import com.example.iot_java_mobile.Adaptor.SeeAllProductAdapter;
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


public class SeeAllProductFragment extends Fragment {


    public SeeAllProductFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_all_products, container, false);
        Bundle bundle = this.getArguments();
        List<Product> productList = (List<Product>) bundle.getSerializable("AllProductsAttached");


        RecyclerView productRecyclerView =v.findViewById(R.id.all_product_recycler_view);
        SeeAllProductAdapter seeAllProductAdapter = new SeeAllProductAdapter(productList);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        productRecyclerView.setLayoutManager(layoutManagerProduct);
        productRecyclerView.setAdapter(seeAllProductAdapter);
        seeAllProductAdapter.setOnItemClickListener(new SeeAllProductAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Fragment productFragment = new ProductFragment();
                Product product = productList.get(position);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("GivingProduct", product);
                productFragment.setArguments(bundle1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, productFragment).commit();

            }
        });
        return v;



    }
}