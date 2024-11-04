package com.example.beershop.Models.Voucher;

public class VoucherData {

    String category;
    String type;
    String start_date;
    String expiration_date;
    Code_Config code_config;
    Discount discount;
    Redemption redemption;
    Metadata metadata;

    public VoucherData(String category, String type, String start_date, String expiration_date, Code_Config code_config, Discount discount, Redemption redemption, Metadata metadata) {
        this.category = category;
        this.type = type;
        this.start_date = start_date;
        this.expiration_date = expiration_date;
        this.code_config = code_config;
        this.discount = discount;
        this.redemption = redemption;
        this.metadata = metadata;

}

    public Code_Config getCode_config() {
        return code_config;
    }

    public void setCode_config(Code_Config code_config) {
        this.code_config = code_config;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Redemption getRedemption() {
        return redemption;
    }

    public void setRedemption(Redemption redemption) {
        this.redemption = redemption;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
