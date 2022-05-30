package com.example.iot_java_mobile.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;

import java.util.List;

public class EstablishmentsHomeAdapter extends RecyclerView.Adapter<EstablishmentsHomeAdapter.ViewHolder> {
    List<Establishment> establishmentList;

    public EstablishmentsHomeAdapter(List<Establishment> establishmentList) {
        this.establishmentList = establishmentList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        //        ImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_est_name);
//            logo = itemView.findViewById(R.id.home_product_image);
        }
    }

    @NonNull
    @Override
    public EstablishmentsHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_est_items, parent, false);
        return new EstablishmentsHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstablishmentsHomeAdapter.ViewHolder holder, int position) {
        holder.name.setText(this.establishmentList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.establishmentList.size();
    }


}
