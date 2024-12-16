package com.pluralsight.dealership.model;


public class SalesContract extends Contract {
    private double salesTaxAmount = .05;
    private double recordingFee = 100.00;
    private double processingFeeUnder10000 = 295.00;
    private double processingFee10000andOver = 495.00;
    private boolean financing;
    private double vehiclePrice;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double vehiclePrice, boolean financing) {
        super(date, customerName, customerEmail, vehicleSold);
        this.vehiclePrice = vehiclePrice;
        this.financing = financing;

        super.setTotalPrice(getTotalPrice());
        super.setMonthlyPayment(getMonthlyPayment());
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(double salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFeeUnder10000() {
        return processingFeeUnder10000;
    }

    public void setProcessingFeeUnder10000(double processingFeeUnder10000) {
        this.processingFeeUnder10000 = processingFeeUnder10000;
    }

    public double getProcessingFee10000andOver() {
        return processingFee10000andOver;
    }

    public void setProcessingFee10000andOver(double processingFee10000andOver) {
        this.processingFee10000andOver = processingFee10000andOver;
    }

    public boolean isFinancing() {
        return financing;
    }

    public void setFinancing(boolean financing) {
        this.financing = financing;
    }

    public double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    @Override
    public double getTotalPrice() {
        double processingFee = (vehiclePrice < 10000) ? processingFeeUnder10000 : processingFee10000andOver; // sets processing fee
        double salesTax = vehiclePrice * salesTaxAmount; // sets sales tax

        return vehiclePrice + salesTax + recordingFee + processingFee; // calculates and returns total price
    }

    @Override
    public double getMonthlyPayment(){
        if(!financing) { // If there is no loan
            return 0;
        }

        double loanAmount = getTotalPrice(); // total finance amount
        double interestRate = (vehiclePrice < 10000) ? 0.0525 : 0.0425; // sets interest rate depending on vehicle price
        int months = (vehiclePrice < 10000) ? 24 : 48; // sets month amount depending on vehicle amount

        double monthlyRate = interestRate / 12; // calculates monthly rate

        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months)); // calculates and returns monthly payment
    }
    @Override
    public String toString(){
        return String.format("SALE | %s | %s | %s | %s | %s | $%.2f | $%.2f | $%.2f | $%.2f",
                financing ? "YES" : "No",getDate(),getCustomerName(),getCustomerEmail(),getVehicleSold(),vehiclePrice * salesTaxAmount,recordingFee,getTotalPrice(),getMonthlyPayment());
    }

}
