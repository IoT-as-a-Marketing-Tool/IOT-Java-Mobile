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
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Adaptor.FavoriteBrandAdapter;
import com.example.iot_java_mobile.Adaptor.NotificationAdapter;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.Brand;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {
    List<AdCampaign>notifications;

    public NotificationFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_notifications, container, false);
        notifications = new ArrayList<>();
        SessionManager sessionManager = new SessionManager(getContext());
        String token= "Bearer "+ sessionManager.getAuthToken();

        RecyclerView recyclerView = v.findViewById(R.id.notification_recycler_view);
        NotificationAdapter notificationAdapter = new NotificationAdapter(notifications);
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerProduct);
        recyclerView.setAdapter(notificationAdapter);

        APIInterface apiInterface = APIClient.getRetrofitClient();
        apiInterface.getNotifications(MainActivity.custID, token).enqueue(new Callback<List<AdCampaign>>() {
            @Override
            public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Log.e("Don", "onResponse: "+ response.body() );
                    notifications.addAll(response.body());
                    notificationAdapter.notifyDataSetChanged();
//                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<AdCampaign>> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry this is not working right now, Try again later ",Toast.LENGTH_SHORT).show();
            }
        });
        notificationAdapter.setOnItemClickListener(new NotificationAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getContext(), AdsPage.class);
                List<AdCampaign> adCampaignList = new ArrayList<>();
                adCampaignList.add(notifications.get(position));
                intent.putExtra("GivingCampaignList", (Serializable) adCampaignList);
                List<Establishment> establishmentList = new ArrayList<>();
                intent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);
                List<Integer> campaignId = new ArrayList<>();
                campaignId.add(notifications.get(position).getId());
                intent.putExtra("UnFavoriteCampaignList", (Serializable) campaignId);
                startActivity(intent);
                notifications.remove(campaignId.get(0));
                notificationAdapter.notifyDataSetChanged();
            }
        });

        TextView clearAll = v.findViewById(R.id.notification_clear_all);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<Integer> campaignIds = new ArrayList<>();
                for(AdCampaign adCampaign: notifications){
                    int id = adCampaign.getId();
                    apiInterface.deleteNotification(id, MainActivity.custID, token).enqueue(new Callback<List<AdCampaign>>() {
                        @Override
                        public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {

                        }

                        @Override
                        public void onFailure(Call<List<AdCampaign>> call, Throwable t) {

                        }
                    });
                }
                notifications = new ArrayList<>();
                notificationAdapter.notifyDataSetChanged();

            }
        });
        return v;

    }
}