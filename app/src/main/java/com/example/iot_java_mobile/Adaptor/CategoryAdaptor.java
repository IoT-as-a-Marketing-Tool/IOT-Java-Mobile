//package com.example.iot_java_mobile.Adaptor;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.iot_java_mobile.Domain.BrandsDomain;
//import com.example.iot_java_mobile.R;
//
//import java.util.ArrayList;
//
//public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
//    ArrayList<BrandsDomain> brandsDomains;
//    public CategoryAdaptor(ArrayList<BrandsDomain> brandsDomains) {
//        this.brandsDomains = brandsDomains;
//    }
//
//
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.,parent,false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.brandName.setText(brandsDomains.get(position).getTitle());
//        String logoUrl="";
//        switch (position){
//            case 0:{
//                logoUrl="";
//            }
//            case 1:{
//                logoUrl="";
//            }
//            case 2:{
//                logoUrl="";
//            }
//            case 3:{
//                logoUrl="";
//            }
//            case 4:{
//                logoUrl="";
//            }
//        }
//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(logoUrl,"drawable", holder.itemView.getContext().getPackageName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public  class ViewHolder extends  RecyclerView.ViewHolder {
//        TextView brandName;
//        ImageView brandLogos;
//        ConstraintLayout brandLayout;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            brandName = itemView.findViewById(R.id.brandName);
//            brandLogos = itemView.findViewById(R.id.brandLogo);
//            brandLayout = itemView.findViewById(R.id.brandLayout);
//        }
//    }
//
//}
