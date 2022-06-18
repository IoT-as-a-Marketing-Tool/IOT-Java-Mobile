package com.example.iot_java_mobile.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_java_mobile.Activity.AdEstablishmentsPage;
import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.BrandPage;
import com.example.iot_java_mobile.Activity.ProductPage;
import com.example.iot_java_mobile.Domain.Ad;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.AdItem;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder>{
    public List<Establishment> establishmentList;
    public List<AdCampaign> campaignList;
    private static AdsAdapter.ClickListener clickListener;
    public View.OnClickListener establishmentClickListener;
    ConstraintSet set = new ConstraintSet();





    public AdsAdapter(List<AdCampaign> campaignList, List<Establishment> establishmentList){
        this.campaignList = campaignList;
        this.establishmentList = establishmentList;
    }



    @NonNull
    @Override
    public AdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ad_page, parent, false);
        return new AdsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsAdapter.ViewHolder holder, int position) {
        AdCampaign campaign = this.campaignList.get(position);
        String establishment_name = (this.establishmentList.size()>0)? this.establishmentList.get(position).getName(): "View all Establishments";
        Ad ad = campaign.getAd();
        holder.establishment_name.setText(establishment_name);
        Picasso.get().load(ad.getAd_image()).into(holder.ad_image);
        int i = holder.getAdapterPosition();

        List<AdItem> adItems= ad.getAdItems();
        if (adItems == null){
            return;
        }
        for (AdItem a: adItems) {
            String url= a.getLink();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Log.e("AD", "ths"+ a.getType());

            String backgroundColor = a.getStyle().getBackgroundColor();
            backgroundColor = holder.toSixHex(backgroundColor);
            String color = a.getStyle().getColor();
            color= holder.toSixHex(color);


            String finalUrl = url;

            switch (a.getType()){
                case "button":
                    float x =(float) (50+  a.getStyle().getX())/100 ;
                    float y = (float) (200+  a.getStyle().getY())/400 ;

                    holder.adButton.setVisibility(View.VISIBLE);
                    holder.adButton.invalidate();

                    Drawable wrappedDrawable = DrawableCompat.wrap(holder.unwrappedDrawable);

                    GradientDrawable sd = (GradientDrawable) holder.adButton.getBackground().mutate();
                    sd.setColor(Color.parseColor(backgroundColor));
                    sd.invalidateSelf();
                    holder.adButton.setTextColor(Color.parseColor(color));
                    holder.adButton.setText(a.getTitle());
                    holder.positionElements(holder.constraintLayout,x,y,holder.adButton.getId());

                    holder.adButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
//                            context.startActivity(browserIntent);
                            }
                    });
                case "link":
                    float xLink =(float) (50+  a.getStyle().getX())/100 ;
                    float yLink = (float) (200+  a.getStyle().getY())/400 ;
                    holder.adLink.setVisibility(View.VISIBLE);
                    holder.adLink.invalidate();

                    holder.adLink.setTextColor(Color.parseColor(color));
                    holder.adLink.setText(a.getTitle());
                    Log.e("y", "y="+yLink);
                    holder.positionElements(holder.constraintLayout,xLink,yLink,holder.adLink.getId());


                    holder.adLink.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
//                            context.startActivity(browserIntent);
                            }
                    });
                case "heading":
                    float xHeading =(float) (50+  a.getStyle().getX())/100 ;
                    float yHeading = (float) (200+  a.getStyle().getY())/400 ;
                    holder.adHeading.setVisibility(View.VISIBLE);
                    holder.adHeading.invalidate();

                    holder.adHeading.setTextColor(Color.parseColor(color));
                    holder.adHeading.setText(a.getTitle());
                    holder.positionElements(holder.constraintLayout,xHeading,yHeading,holder.adHeading.getId());

            }
        }


//        holder.establishment_name.setOnClickListener(establishmentClickListener);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView establishment_name;
        public ImageView ad_image;
        ConstraintLayout constraintLayout;
        Button adButton;
        TextView adLink;
        TextView adHeading;
        Drawable unwrappedDrawable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            establishment_name = itemView.findViewById(R.id.ad_establishment);
            ad_image = itemView.findViewById(R.id.ad_image);
//            itemView.setOnClickListener(establishment_name);
            establishment_name.setOnClickListener(this::onClick);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.ad_constraint);
            adButton = itemView.findViewById(R.id.ad_button_item);
            adLink = itemView.findViewById(R.id.ad_link_item);
            adHeading = itemView.findViewById(R.id.ad_heading_item);
            unwrappedDrawable = AppCompatResources.getDrawable(itemView.getContext(), R.drawable.button_custom);


        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        public String toSixHex(String hex){

            if(hex.length()==4){
                hex = hex.replaceAll("#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])", "#$1$1$2$2$3$3");

            }
            return hex;
        }
        public void positionElements(ConstraintLayout constraintLayout, float x, float y, Integer id){
            set.clone(constraintLayout);
            set.setHorizontalBias(id, x);
            set.setVerticalBias(id,y);
            set.applyTo(constraintLayout);
        }
        public float nearestVal(double val){

            if(val>1 ){
                val= 1;
            }else if (val<0){
                val= 0;
            }
            return (float) val;
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onClickListener()
    }
    public void setOnItemClickListener(AdsAdapter.ClickListener clickListener) {
        AdsAdapter.clickListener = clickListener;
    }
}
