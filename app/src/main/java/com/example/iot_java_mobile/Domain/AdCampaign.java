package com.example.iot_java_mobile.Domain;

import java.io.Serializable;
import java.util.List;

public class AdCampaign implements Serializable {
    int id;
    String name;
    int brand;
    int product;
    List<Establishment> establishments;
    Ad ad;

    public AdCampaign(int id, String name, int brand, int product, List<Establishment> establishments, Ad ad) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.product = product;
        this.establishments = establishments;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBrand() {
        return brand;
    }

    public int getProduct() {
        return product;
    }

    public List<Establishment> getEstablishments() {
        return establishments;
    }

    public Ad getAd() {
        return ad;
    }

    @Override
    public String toString() {
        return "AdCampaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", product=" + product +
                ", establishments=" + establishments +
                ", ad=" + ad +
                '}';
    }
}
