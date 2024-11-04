package com.example.beershop.Models;

public class CreditsData {

    String code, qrCode, surveyName;
    int remaining;

    public CreditsData() {
    }

    public CreditsData(String code, String qrCode, String surveyName) {
        this.code = code;
        this.qrCode = qrCode;
        this.surveyName = surveyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
