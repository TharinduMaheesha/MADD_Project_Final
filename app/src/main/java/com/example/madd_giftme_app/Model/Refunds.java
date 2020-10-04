package com.example.madd_giftme_app.Model;

public class Refunds {

    private String orderid , email , status , reason , requestid;

    public Refunds(String orderid, String email, String status, String reason) {
        this.orderid = orderid;
        this.email = email;
        this.status = status;
        this.reason = reason;
    }

    public Refunds() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }
}
