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
