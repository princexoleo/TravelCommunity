package com.travel.leo.travelcommunity.model;

public class TourModel {

    private String tourId;
    private String userId;

    private String title;
    private String description;
    private String startLocation;
    private String finishLocation;
    private String startDate;
    private String finishDate;

    private String mapImage;
    private String walkingDistance;
    private String coverDistance;
    private String remDistance;
    private String likeStatus;
    private String onlineStatus;



    private ImageDetails uploadImage;

    public TourModel() {

    }

    public TourModel(String title, String description, String startLocation, String finishLocation, String startDate,String finishDate) {

        this.title = title;
        this.description = description;
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.startDate=startDate;
        this.finishDate=finishDate;

    }

    public TourModel(String tourId, String userId, String title, String description, String startLocation,
                     String finishLocation, String startDate, String finishDate,
                     String mapImage, String walkingDistance, String coverDistance,
                     String remDistance, String likeStatus, String onlineStatus) {

        this.tourId = tourId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.mapImage = mapImage;
        this.walkingDistance = walkingDistance;
        this.coverDistance = coverDistance;
        this.remDistance = remDistance;
        this.likeStatus = likeStatus;
        this.onlineStatus = onlineStatus;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public String getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(String walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    public String getCoverDistance() {
        return coverDistance;
    }

    public void setCoverDistance(String coverDistance) {
        this.coverDistance = coverDistance;
    }

    public String getRemDistance() {
        return remDistance;
    }

    public void setRemDistance(String remDistance) {
        this.remDistance = remDistance;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String catagory) {
        this.description = catagory;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(String finishLocation) {
        this.finishLocation = finishLocation;
    }


    public ImageDetails getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(ImageDetails uploadImage) {
        this.uploadImage = uploadImage;
    }
}
