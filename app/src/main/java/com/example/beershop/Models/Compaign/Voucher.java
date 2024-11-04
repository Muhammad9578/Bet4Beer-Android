package com.example.beershop.Models.Compaign;

import com.example.beershop.Models.Voucher.Code_Config;

public class Voucher {

    String type;
    C_Discount discount;
    C_Redemption c_redemption;
    Code_Config code_config;

    public Voucher(String type, C_Discount discount, C_Redemption c_redemption, Code_Config code_config) {
        this.type = type;
        this.discount = discount;
        this.c_redemption = c_redemption;
        this.code_config = code_config;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public C_Discount getDiscount() {
        return discount;
    }

    public void setC_discount(C_Discount discount) {
        this.discount = discount;
    }

    public C_Redemption getC_redemption() {
        return c_redemption;
    }

    public void setC_redemption(C_Redemption c_redemption) {
        this.c_redemption = c_redemption;
    }

    public Code_Config getCode_config() {
        return code_config;
    }

    public void setCode_config(Code_Config code_config) {
        this.code_config = code_config;
    }
}
