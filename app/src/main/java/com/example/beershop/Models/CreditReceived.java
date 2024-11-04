package com.example.beershop.Models;

public class CreditReceived {

    String qrCode, surveyName, quantity;


    public CreditReceived() {
    }

    public CreditReceived(String qrCode, String surveyName, String quantity) {
        this.qrCode = qrCode;
        this.surveyName = surveyName;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

}
