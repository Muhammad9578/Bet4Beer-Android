package com.example.beershop.Models.Compaign;

import com.example.beershop.Models.Voucher.Code_Config;

public class CompaignData {

    String name, start_date, expiration_date;
    int vouchers_count;
    Voucher voucher;
    Com_metadata com_metadata;

    public CompaignData(String name, String start_date, String expiration_date, int vouchers_count, Voucher voucher, Com_metadata com_metadata) {
        this.name = name;
        this.start_date = start_date;
        this.expiration_date = expiration_date;
        this.vouchers_count = vouchers_count;
        this.voucher = voucher;
        this.com_metadata = com_metadata;
    }

    public Com_metadata getCom_metadata() {
        return com_metadata;
    }

    public void setCom_metadata(Com_metadata com_metadata) {
        this.com_metadata = com_metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getVouchers_count() {
        return vouchers_count;
    }

    public void setVouchers_count(int vouchers_count) {
        this.vouchers_count = vouchers_count;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

}
