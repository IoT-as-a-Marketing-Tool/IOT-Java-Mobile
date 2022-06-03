package com.example.iot_java_mobile.Domain;

import com.google.gson.JsonElement;

import java.io.Serializable;

public class Establishment implements Serializable {
    String name;
    String logo;
    String description;
    String est_type;
    transient JsonElement location; //TODO: make it serializable
    String verification_status;

    public Establishment(String name, String logo, String description, String est_type, JsonElement location, String verification_status) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.est_type = est_type;
        this.location = location;
        this.verification_status = verification_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEst_type() {
        return est_type;
    }

    public void setEst_type(String est_type) {
        this.est_type = est_type;
    }

    public JsonElement getLocation() {
        return location;
    }

    public void setLocation(JsonElement location) {
        this.location = location;
    }

    public String getVerification_status() {
        return verification_status;
    }

    public void setVerification_status(String verification_status) {
        this.verification_status = verification_status;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                ", est_type='" + est_type + '\'' +
                ", location=" + location +
                ", verification_status='" + verification_status + '\'' +
                '}';
    }
}
