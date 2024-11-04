package com.example.beershop.Models.Voucher;

public class Redemption {

    int quantity;

    public Redemption(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
