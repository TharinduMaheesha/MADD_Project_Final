/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

/*
 * Class for delivery riders' tasks
 * */
public class AddRider {
    String name;
    String address;
    String phone;
    String email;
    String bike;
    String nic;
    String lison;
    int age;
    //constructor

    public AddRider() { }

    //get and set rider's name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //get and set rider's address
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //get and set rider's phone number
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //get and set rider's email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //get and set rider's bike number
    public String getBike() {
        return bike;
    }
    public void setBike(String bike) {
        this.bike = bike;
    }

    //get and set rider's nic number
    public String getNic() {
        return nic;
    }
    public void setNic(String nic) {
        this.nic = nic;
    }

    //get and set rider's lison number
    public String getLison() {
        return lison;
    }
    public void setLison(String lison) {
        this.lison = lison;
    }

    //get and set rider's age
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
