package com.example.iot_java_mobile.Adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
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

public class FavoriteBrandAdapter extends RecyclerView.Adapter<FavoriteBrandAdapter.ViewHolder> {
    List<Brand> brandList;
    SessionManager sessionManager;

    public FavoriteBrandAdapter(List<Brand> brandList, SessionManager sessionManager) {
        this.brandList = brandList;
        if (this.brandList == null){
            this.brandList = new ArrayList<>();
        }
        this.sessionManager = sessionManager;
    }
//    private static ClickListener clickListener;
    @NonNull
    @Override
    public FavoriteBrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_brands, parent, false);
        return new FavoriteBrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteBrandAdapter.ViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        holder.brandDescription.setText(brand.getDescription());
        Picasso.get().load(brand.getLogo()).into(holder.brandLogo);
        String token= "Bearer "+ sessionManager.getAuthToken();
        holder.removeFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: REMOVE BRAND
                APIInterface apiInterface = APIClient.getRetrofitClient();
                apiInterface.unfavoriteBrand(brand.getId(), MainActivity.custID,token).enqueue(new Callback < List < Brand >> () {
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
                brandList.remove(holder.getAdapterPosition());
//                holder.constraintLayout. setVisibility(View.GONE);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView brandName;
        TextView brandDescription;
        ImageView brandLogo;
        Button removeFavBtn;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.fav_brand_name);
            brandDescription = itemView.findViewById(R.id.fav_brand_desc);
            brandLogo = itemView.findViewById(R.id.fav_brand_logo);
            removeFavBtn = itemView.findViewById(R.id.remove_fav_brand);
            constraintLayout = itemView.findViewById(R.id.fav_brand_constraint_layout);
//            itemView.setOnClickListener(this);

        }

    }
//    public interface ClickListener {
//        void onItemClick(int position, View v);
//    }
//    public void setOnItemClickListener(FavoriteBrandAdapter.ClickListener clickListener) {
//        FavoriteBrandAdapter.clickListener = clickListener;
//    }
}
