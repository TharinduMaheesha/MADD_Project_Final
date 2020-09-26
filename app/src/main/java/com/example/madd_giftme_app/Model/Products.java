package com.example.madd_giftme_app.Model;

public class Products {

    private String product_id, product_name, product_description, product_image, product_event, product_date, product_time, product_price, product_availability;

    public Products() {
    }

    public Products(String product_id, String product_name, String product_description, String product_image, String product_event, String product_date, String product_time, String product_price, String product_availability) {
        this.product_id = product_id ;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_image = product_image ;
        this.product_event = product_event ;
        this.product_date = product_date ;
        this.product_time = product_time ;
        this.product_price = product_price ;
        this.product_availability = product_availability ;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String pid) {
        this.product_id = pid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String name) {
        this.product_name = name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String description) {
        this.product_description = description;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_event() {
        return product_event;
    }

    public void setProduct_event(String product_event) {
        this.product_event = product_event;
    }

    public String getProduct_date() {
        return product_date;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public String getProduct_time() {
        return product_time;
    }

    public void setProduct_time(String product_time) {
        this.product_time = product_time;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_availability() {
        return product_availability;
    }

    public void setProduct_availability(String product_availability) {
        this.product_availability = product_availability;
    }
}
