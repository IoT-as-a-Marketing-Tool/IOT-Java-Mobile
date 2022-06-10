package com.example.iot_java_mobile.Domain;

public class User {
    private String username;
    private String email;
    public String first_name;
    public String last_name;
    public Integer userType;
    public String password;

    public User(String username, String email, String first_name, String last_name, Integer userType,String password) {
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.userType = userType;
        this.password= password;
    }

    public String getUsername() {
        return username;
    }
}
