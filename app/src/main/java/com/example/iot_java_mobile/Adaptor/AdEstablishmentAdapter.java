package com.example.iot_java_mobile.Adaptor;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdEstablishmentAdapter extends RecyclerView.Adapter<AdEstablishmentAdapter.ViewHolder> {
    List<Establishment> establishmentList;

    public AdEstablishmentAdapter(List<Establishment> establishmentList) {
        this.establishmentList = establishmentList;
        if (this.establishmentList == null){
            this.establishmentList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public AdEstablishmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_establishment_item, parent, false);
        return new AdEstablishmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdEstablishmentAdapter.ViewHolder holder, int position) {
        Establishment establishment = establishmentList.get(position);
        holder.estName.setText(establishment.getName());
        holder.estDescription.setText(establishment.getDescription());
        Picasso.get().load(establishment.getLogo()).into(holder.estLogo);
        
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Don", "onLocationClick: "+ establishment.getLocation() );
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q="+establishment.getLocation().getLatitude() +","+ establishment.getLocation().getLongitude()));
                view.getContext().startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return establishmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView estName;
        TextView estDescription;
        ImageView estLogo;
        Button location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            estName = itemView.findViewById(R.id.est_item_est_name);
            estLogo = itemView.findViewById(R.id.est_item_est_logo);
            estDescription = itemView.findViewById(R.id.est_item_est_description);
            location = itemView.findViewById(R.id.est_item_location);
            

        }


    }
}
