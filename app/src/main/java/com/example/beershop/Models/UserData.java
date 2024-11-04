package com.example.beershop.Models;

public class UserData {

    String name, email, image, dob;

    public UserData() {
    }

    public UserData(String name, String email, String image, String dob) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
