package com.example.beershop.Models.Customer;

public class CustomerData{

    String source_id, name, email, description;
    Address address;
    C_Metadata c_metadata;

    public CustomerData(String source_id, String name, String email, String description, Address address, C_Metadata c_metadata) {
        this.source_id = source_id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.address = address;
        this.c_metadata = c_metadata;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public C_Metadata getC_metadata() {
        return c_metadata;
    }

    public void setC_metadata(C_Metadata c_metadata) {
        this.c_metadata = c_metadata;
    }
}
