package com.example.iot_java_mobile.Domain;

import com.google.gson.JsonElement;

import java.io.Serializable;

public class Establishment implements Serializable {

    public static class Location implements Serializable {
        double latitude;
        double longitude;

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }
    }
    int id;
    String name;
    String logo;
    String description;
    String est_type;
    Location location; //TODO: make it serializable
    String verification_status;
    double distanceFromPhone;
    

    public Establishment(int id, String name, String logo, String description, String est_type, Location location, String verification_status) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.est_type = est_type;
        this.location = location;
        this.verification_status = verification_status;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDistanceFromPhone() {
        return distanceFromPhone;
    }

    public void setDistanceFromPhone(double distanceFromPhone) {
        this.distanceFromPhone = distanceFromPhone;
    }

    public String getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public String getEst_type() {
        return est_type;
    }

    public Location getLocation() {
        return location;
    }

    public String getVerification_status() {
        return verification_status;
    }


    @Override
    public String toString() {
        return "Establishment{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                ", est_type='" + est_type + '\'' +
                ", location=" + location +
                ", verification_status='" + verification_status + '\'' +
                '}';
    }
}
