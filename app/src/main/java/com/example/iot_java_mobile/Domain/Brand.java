package com.example.iot_java_mobile.Domain;



import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Brand implements Serializable {

    public static class Color implements Serializable {
        String primaryColor;
        String secondaryColor;

        public Color(String primaryColor, String secondaryColor) {
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
        }
    }
    int id;

    private String name;

    private String logo;
    String description;
    String brand_type;
    String brand_colors;
    Color colors;
    String verification_status;
    List<Product> products;

    public Brand(String name, String logo, String description, String brand_type, Color colors, String verification_status, List<Product> products) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.brand_type = brand_type;
        this.colors = colors;
        this.verification_status = verification_status;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand_type() {
        return brand_type;
    }

    public Color getColors() {
        return colors;
    }

    public String getVerification_status() {
        return verification_status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Brand(String name, String logo, String description, String brand_type, Color colors, String verification_status) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.brand_type = brand_type;
        this.colors = colors;
        this.verification_status = verification_status;
    }

    public Brand(int id, String name, String logo, String description, String brand_type, String brand_colors, Color colors, String verification_status, List<Product> products) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.brand_type = brand_type;
        this.brand_colors = brand_colors;
        this.colors = colors;
        this.verification_status = verification_status;
        this.products = products;
    }


    public String getBrand_colors() {
        return brand_colors;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                ", brand_type='" + brand_type + '\'' +
                ", colors=" + colors +
                ", verification_status='" + verification_status + '\'' +
                ", products=" + products +
                '}';
    }
}
