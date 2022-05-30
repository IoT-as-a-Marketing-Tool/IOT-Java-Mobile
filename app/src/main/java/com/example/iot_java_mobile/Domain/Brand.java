package com.example.iot_java_mobile.Domain;

public class Brand {
    private String name;
    private String logo;

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

    public Brand(String title, String brandLogo){
        this.name = title;
        this.logo = brandLogo;
    }
}
