package com.example.iot_java_mobile.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private int id;
    @JsonProperty("profile")
    private Profile profile;
    private ArrayList<Integer> favorites;
    private ArrayList<Integer> notifications;

    public int getId() {
        return id;
    }

    public Customer(int id, Profile profile, ArrayList<Integer> favorites, ArrayList<Integer> notifications) {
        this.id = id;
        this.profile = profile;
        this.favorites = favorites;
        this.notifications = notifications;
    }

    public Customer(Profile profile, ArrayList<Integer> favorites, ArrayList<Integer> notifications) {
        this.profile = profile;
        this.favorites = favorites;
        this.notifications = notifications;
    }

    public Customer() {
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<Integer> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Integer> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Integer> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Integer> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "profile=" + profile +
                ", favorites=" + favorites +
                ", notifications=" + notifications +
                '}';
    }
}
