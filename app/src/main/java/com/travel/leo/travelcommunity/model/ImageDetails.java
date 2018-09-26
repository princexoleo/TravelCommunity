package com.travel.leo.travelcommunity.model;

public class ImageDetails {
    private String name;
    private String longitude;
    private String langitude;
    private String imageId;

    public ImageDetails() {
    }

    public ImageDetails(String name, String longitude, String langitude, String imageId) {
        this.name = name;
        this.longitude = longitude;
        this.langitude = langitude;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLangitude() {
        return langitude;
    }

    public void setLangitude(String langitude) {
        this.langitude = langitude;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
