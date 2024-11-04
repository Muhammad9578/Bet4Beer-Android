package com.example.beershop.Models;

public class ImageData {

    String uid, userName, email, screenShotImage, status;

    public ImageData() {
    }

    public ImageData(String uid, String userName, String email, String screenShotImage, String status) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.screenShotImage = screenShotImage;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScreenShotImage() {
        return screenShotImage;
    }

    public void setScreenShotImage(String screenShotImage) {
        this.screenShotImage = screenShotImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
