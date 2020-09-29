package com.example.madd_giftme_app.Model;

public class Delivery {

    private String address, city, customerid, delivery_status, email, name, orderid, phone, postalCode ;

    public Delivery() {
    }

    public Delivery(String address, String city, String customerid, String delivery_status, String email, String name,
                    String orderid, String phone, String postalCode) {
        this.address = address;
        this.city = city;
        this.customerid = customerid;
        this.delivery_status = delivery_status;
        this.email = email;
        this.name = name;
        this.orderid = orderid;
        this.phone = phone;
        this.postalCode = postalCode;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
