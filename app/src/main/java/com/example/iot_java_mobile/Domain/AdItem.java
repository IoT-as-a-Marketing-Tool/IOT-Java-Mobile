package com.example.iot_java_mobile.Domain;

import java.io.Serializable;
import java.util.HashMap;

public class AdItem implements Serializable {
    public static class Style implements Serializable{
        public HashMap<String, Object> style;

        public Style(HashMap<String, Object> style) {
            this.style = style;
        }

        public HashMap<String, Object> getStyle() {
            return style;
        }

        @Override
        public String toString() {
            return "Style{" +
                    "style=" + style +
                    '}';
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
