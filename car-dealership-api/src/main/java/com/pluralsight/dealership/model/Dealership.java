package com.pluralsight.dealership.model;

public class Dealership {
    // dealership fields
    private int id;
    private String name;
    private String address;
    private String phoneNumber;

    //dealership constructor
    public Dealership(int id,String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    // getters
    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
