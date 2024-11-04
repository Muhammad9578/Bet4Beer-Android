package com.example.beershop.Models.Publication;

public class Customer {

    String source_id, email, name;

    public Customer(String source_id, String email, String name) {
        this.source_id = source_id;
        this.email = email;
        this.name = name;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
