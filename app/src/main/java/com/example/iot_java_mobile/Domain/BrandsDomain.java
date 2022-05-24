package com.example.iot_java_mobile.Domain;

public class BrandsDomain {
    private String title;
    private String brandLogo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public BrandsDomain(String title, String brandLogo){
        this.title = title;
        this.brandLogo = brandLogo;
    }
}
