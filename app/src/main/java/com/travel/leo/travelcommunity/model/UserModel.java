package com.travel.leo.travelcommunity.model;

public class UserModel {
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private String Gender;
    private String City;
    private String profileImage;
    private String totaltour;
    private String onlineStatus;

    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String phone, String gender, String city) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Gender = gender;
        City = city;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTotaltour() {
        return totaltour;
    }

    public void setTotaltour(String totaltour) {
        this.totaltour = totaltour;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
