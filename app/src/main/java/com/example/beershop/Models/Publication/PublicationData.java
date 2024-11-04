package com.example.beershop.Models.Publication;

public class PublicationData {

    Campaign campaign;
    Customer customer;
    P_Metadata p_metadata;
    String voucher;

    public PublicationData(Campaign campaign, Customer customer, P_Metadata p_metadata, String voucher) {
        this.campaign = campaign;
        this.customer = customer;
        this.p_metadata = p_metadata;
        this.voucher = voucher;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public P_Metadata getP_metadata() {
        return p_metadata;
    }

    public void setP_metadata(P_Metadata p_metadata) {
        this.p_metadata = p_metadata;
    }
}
