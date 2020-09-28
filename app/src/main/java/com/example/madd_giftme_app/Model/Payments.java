package com.example.madd_giftme_app.Model;

public class Payments {

    private  String payid , date , email , orderid , amount;

    public Payments(String payid, String date, String email, String orderid, String amount) {
        this.payid = payid;
        this.date = date;
        this.email = email;
        this.orderid = orderid;
        this.amount = amount;
    }

    public Payments() {
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
