package com.example.iot_java_mobile.Domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
    private double id;
    private String gender;
    private String birthday;
    private String country;



    public Profile(String gender, String birthday, String country) {
        this.gender = gender;
        this.birthday = birthday;
        this.country = country;

    }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
