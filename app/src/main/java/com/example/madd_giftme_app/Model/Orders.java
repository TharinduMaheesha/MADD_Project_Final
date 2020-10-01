package com.example.madd_giftme_app.Model;

import java.util.List;

public class Orders {

    private String orderid , email , pid , did, date , time , quantity , discount , order_status , payment_status , delivery_status;
    private String total ;

    public Orders(String orderid, String email, String pid, String did, String date, String time, String quantity, String discount, String order_status, String payment_status, String delivery_status, String total) {
        this.orderid = orderid;
        this.email = email;
        this.pid = pid;
        this.did = did;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.discount = discount;
        this.order_status = order_status;
        this.payment_status = payment_status;
        this.delivery_status = delivery_status;
        this.total = total;
    }

    public Orders() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPid() {
        return pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}

