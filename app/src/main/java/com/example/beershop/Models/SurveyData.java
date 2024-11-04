package com.example.beershop.Models;

public class SurveyData {

    String code, qrCode , surveyName, quantity;

    public SurveyData() {
    }

    public SurveyData(String code, String qrCode, String surveyName, String quantity) {
        this.code = code;
        this.qrCode = qrCode;
        this.surveyName = surveyName;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
