/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;


/*
 * Class for delivering tasks
 * */
public class Delivery {
    String name;
    String address;
    String phone;
    String items;
    double price;
    String boyid;
    String date;

    //constructor
    public Delivery() {
    }

    //get and set name of the  receiver
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //get and set address of the receiver
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //get and set phone number of the  receiver
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //get and set item names of the order
    public String getItems() {
        return items;
    }
    public void setItems(String items) {
        this.items = items;
    }

    //get and set total price of the order
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    //get and set assigned delivery bou's id for an oder
    public String getBoyid() {
        return boyid;
    }
    public void setBoyid(String boyid) {
        this.boyid = boyid;
    }

    //get and set the date of the delivery
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
