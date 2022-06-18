package com.example.iot_java_mobile.Domain;

import android.media.Image;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Serializable {
    int id;
    String name;
    String image;
    Details details;
    int brand;
    List<AdCampaign> adcampaigns;


    public static class Details implements Serializable {
//        String price;
        public HashMap<String, Object> querys;


        public Details(HashMap<String, Object> querys) {
            this.querys = querys;
        }

        @Override
        public String toString() {
            return " " + querys;
        }
    }
    public Product(String name, String image, Details details, List<AdCampaign> adcampaigns) {
        this.name = name;
        this.image = image;
        this.details = details;
        this.adcampaigns = adcampaigns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public List<AdCampaign> getAdcampaigns() {
        return adcampaigns;
    }

    public int getBrand() {
        return brand;
    }

//    class JsonElement extends JsonElement implements Serializable{
//
//        @Override
//        public JsonElement deepCopy() {
//            return null;
//        }
//    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", details=" + details +
                ", brand=" + brand +
                ", adcampaigns=" + adcampaigns +
                '}';
    }
}
