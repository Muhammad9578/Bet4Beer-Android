package com.example.beershop.Models.Voucher;

public class Discount {

    int percent_off;
    String type;

    public Discount(int percent_off, String type) {
        this.percent_off = percent_off;
        this.type = type;
    }

    public int getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(int percent_off) {
        this.percent_off = percent_off;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
