package com.example.beershop.Models.Publication;

public class P_Metadata {

    boolean test;
    String provider;

    public P_Metadata(boolean test, String provider) {
        this.test = test;
        this.provider = provider;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
