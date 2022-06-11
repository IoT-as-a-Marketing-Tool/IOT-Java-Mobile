package com.example.iot_java_mobile.Domain;

import java.io.Serializable;

public class EstProduct implements Serializable {
    int id;
    Establishment establishment;
    AdCampaign campaign;

    public EstProduct(int id, Establishment establishment, AdCampaign campaign) {
        this.id = id;
        this.establishment = establishment;
        this.campaign = campaign;
    }

    public int getId() {
        return id;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public AdCampaign getCampaign() {
        return campaign;
    }

    @Override
    public String toString() {
        return "EstProduct{" +
                "id=" + id +
                ", establishment=" + establishment +
                ", campaign=" + campaign +
                '}';
    }
}
