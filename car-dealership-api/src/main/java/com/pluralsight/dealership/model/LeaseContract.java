package com.pluralsight.dealership.model;

import com.pluralsight.dealership.model.Contract;

public class LeaseContract extends Contract {
    private double leaseFeeRate = 0.07;
    private double financeRate = 0.04;
    private int leaseMonths = 36;
    private double originalPrice;
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double originalPrice) {
        super(date, customerName, customerEmail, vehicleSold);
        this.originalPrice = originalPrice;
        this.expectedEndingValue = originalPrice * .50; // calculates expected ending value
        this.leaseFee = originalPrice * leaseFeeRate; // calculates lease fee

        super.setTotalPrice(getTotalPrice());
        super.setMonthlyPayment(getMonthlyPayment());
    }

    public double getLeaseFeeRate() {
        return leaseFeeRate;
    }

    public void setLeaseFeeRate(double leaseFeeRate) {
        this.leaseFeeRate = leaseFeeRate;
    }

    public double getFinanceRate() {
        return financeRate;
    }

    public void setFinanceRate(double financeRate) {
        this.financeRate = financeRate;
    }

    public int getLeaseMonths() {
        return leaseMonths;
    }

    public void setLeaseMonths(int leaseMonths) {
        this.leaseMonths = leaseMonths;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    @Override
    public double getTotalPrice(){
        return expectedEndingValue + leaseFee;
    }

    @Override
    public double getMonthlyPayment(){
        double financedAmount = getTotalPrice(); // amount financed

        // calculates monthly payment using formula
        double monthlyRate = financeRate / 12; // calculates monthly interest Rate
        return (financedAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -leaseMonths));
    }

    @Override
    public String toString(){
        return String.format("LEASE | %s | %s | %s | %s | $%.2f | $%.2f | $%.2f | $%.2f",
                getDate(),getCustomerName(),getCustomerEmail(),getVehicleSold(),getExpectedEndingValue(),getLeaseFeeRate(),getTotalPrice(),getMonthlyPayment());
    }

}
