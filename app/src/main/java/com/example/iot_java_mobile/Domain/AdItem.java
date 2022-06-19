package com.example.iot_java_mobile.Domain;

import java.io.Serializable;

public class AdItem implements Serializable {
    public static class Style implements Serializable{
        String fontSize;
        String backgroundColor;
        String color;
        Double x;
        Double y;

        public Style(String fontSize, String backgroundColor, String color, Double x, Double y) {
            this.fontSize = fontSize;
            this.backgroundColor = backgroundColor;
            this.color = color;
            this.x = x;
            this.y = y;
        }
        public  Style(){

        }

        public String getFontSize() {
            return fontSize;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public String getColor() {
            return color;
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }
    }

    int id;
    String title;
    String type;
    String link;
    boolean active;
    Style style;
    int ad;

    public AdItem(int id, String title, String type, String link, boolean active, Style style, int ad) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.link = link;
        this.active = active;
        this.style = style;
        this.ad = ad;


    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public boolean isActive() {
        return active;
    }

    public Style getStyle() {
        return style;
    }

    public int getAd() {
        return ad;
    }

    @Override
    public String toString() {
        return "AdItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", active=" + active +
                ", style=" + style +
                ", ad=" + ad +
                '}';
    }
}
