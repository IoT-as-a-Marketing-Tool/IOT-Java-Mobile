package com.example.iot_java_mobile.Domain;

import android.media.Image;

import com.google.gson.JsonElement;

import java.io.Serializable;

public class Product implements Serializable {
    String name;
    String image;
    transient JsonElement details;

    public Product(String name, String image, JsonElement details) {
        this.name = name;
        this.image = image;
        this.details = details;
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

    public JsonElement getDetails() {
        return details;
    }

    public void setDetails(JsonElement details) {
        this.details = details;
    }

//    class JsonElement extends JsonElement implements Serializable{
//
//        @Override
//        public JsonElement deepCopy() {
//            return null;
//        }
//    }
}
