package com.example.beershop.Models.Compaign;

public class C_Discount {

    String percent_off, type;

    public C_Discount(String percent_off, String type) {
        this.percent_off = percent_off;
        this.type = type;
    }

    public String getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(String percent_off) {
        this.percent_off = percent_off;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
