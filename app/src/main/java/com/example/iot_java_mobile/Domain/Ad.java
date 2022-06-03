package com.example.iot_java_mobile.Domain;

import java.io.Serializable;
import java.util.List;

public class Ad implements Serializable {
    int id;
    String ad_image;
    List<AdItem> adItems;

    public Ad(int id, String ad_image, List<AdItem> adItems) {
        this.id = id;
        this.ad_image = ad_image;
        this.adItems = adItems;
    }

    public int getId() {
        return id;
    }

    public String getAd_image() {
        return ad_image;
    }

    public List<AdItem> getAdItems() {
        return adItems;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", ad_image='" + ad_image + '\'' +
                ", adItems=" + adItems +
                '}';
    }
}
