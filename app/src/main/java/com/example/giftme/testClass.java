package com.example.giftme;

public class testClass {

    private String name,address,id;

    public testClass(String id, String name, String address) {
        this.name = name;
        this.address = address;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
