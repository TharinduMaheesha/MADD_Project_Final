package com.example.madd_giftme_app.Model;

public class OrderItems {

    String pid , quantity , orderid;

    public OrderItems(String pid, String quantity , String orderid) {
        this.pid = pid;
        this.quantity = quantity;
        this.orderid=orderid;
    }

    public OrderItems() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
