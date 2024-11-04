package com.example.beershop.Models.Voucher;

public class Metadata {

    boolean test;
    String locale;

    public Metadata(boolean test, String locale) {
        this.test = test;
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
