package com.pluralsight.dealership.model;

public class Vehicle {

    //Vehicle fields
    private String vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;
    private boolean sold;


    // Vehicle constructor


    public Vehicle(String vin, int year, String make, String model, String vehicleType, String color, int odometer, double price, boolean sold) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
        this.sold = sold;
    }

    public Vehicle() {

    }

    // Getters and setters
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public boolean isSold() {
        return sold;
    }

    @Override
    public String toString() {
        return String.format(" | %s | %d | %s | %s | %s | %s | %d | $%.2f | \n",
                vin,year,make,model,vehicleType,color,odometer,price);
    }
}
