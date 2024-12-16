package com.pluralsight.dealership.datamanagers;

import com.pluralsight.dealership.model.Contract;
import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.SalesContract;
import com.pluralsight.dealership.model.Vehicle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


// TO DO
// remove vehicle from inventory after sale or lease

public class ContractFileManager {

    static Scanner scanner = new Scanner(System.in);



// depending on instance of contract writes different output
public static void saveContract(Contract contract) {
    try {
        System.out.println(contract);
        File file = new File("src/main/resources/Data/contracts.csv");
        boolean isNewFile = !file.exists() || file.length() == 0;
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter buffWriter = new BufferedWriter(fileWriter);
        if(contract instanceof SalesContract) {
            double salesTaxAmount = ((SalesContract) contract).getSalesTaxAmount() * ((SalesContract) contract).getVehiclePrice();
            double processingFee = ((SalesContract) contract).getVehiclePrice() < 10000 ? 295 : 495;
            String isFinanced = ((SalesContract) contract).isFinancing() ? "Yes" : "No";
//            buffWriter.write("SALE" + "|" +
//                    contract.getDate() + "|" +
//                    contract.getCustomerName() + "|" +
//                    contract.getCustomerEmail() + "|" +
//                    contract.getVehicleSold() + "|" +
//                    salesTaxAmount + "|" +
//                    ((SalesContract) contract).getRecordingFee() + "|" +
//                    processingFee + "|" +
//                    contract.getTotalPrice() + "|" +
//                    isFinanced + "|" +
//                    String.format("$%.2f",contract.getMonthlyPayment()));
        }
        else if(contract instanceof LeaseContract) {

                buffWriter.write("LEASE" + "|" +
                        contract.getDate() + "|" +
                        contract.getCustomerName() + "|" +
                        contract.getCustomerEmail() + "|" +
                        contract.getVehicleSold() + "|" +
                        ((LeaseContract) contract).getExpectedEndingValue() + "|" +
                        String.format("$%.2f", ((LeaseContract) contract).getLeaseFee()) + "|" +
                        String.format("$%.2f", contract.getTotalPrice()) + "|" +
                        String.format("$%.2f", contract.getMonthlyPayment()));
            }
            buffWriter.newLine();


            buffWriter.close();
        } catch(IOException e){
            System.out.println("Error saving contract to file");
            e.printStackTrace();
        }


    }

    // Method to check if car is older than 3 years
    public static boolean okayToLeaseMethod(int year){
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int targetYear = currentYear - 3;

        return year >= targetYear;


    }
    // prompts user for info and makes instance of contract depending on if sale or lease
    public static Contract makeContract (String contractType, Vehicle vehicle){
        if (contractType.equalsIgnoreCase("saleContract")) {
            String date = promptMethod("Please Enter Date YYYY/MM/dd: ");
            String customerName = promptMethod("Please Enter Full Name: ");
            String customerEmail = promptMethod("Please enter email: ");
            Vehicle vehicleSold = vehicle;
            double vehiclePrice = vehicle.getPrice();
            boolean financing = isFinancingPrompt();
            SalesContract newSC = new SalesContract(date, customerName, customerEmail, vehicleSold, vehiclePrice, financing);
            System.out.println(newSC);
            return newSC;
        } else {
            String date = promptMethod("Please Enter Date YYYY/MM/dd: ");
            String customerName = promptMethod("Please Enter Full Name: ");
            String customerEmail = promptMethod("Please enter email: ");
            Vehicle vehicleSold = vehicle;
            double vehiclePrice = vehicle.getPrice();
            LeaseContract newLC = new LeaseContract(date, customerName, customerEmail, vehicleSold, vehiclePrice);
            return newLC;
        }

    }

    // checks if user is financing vehicle
    public static boolean isFinancingPrompt () {

        boolean isFinancing = false;
        boolean validInput = false;

        while (!validInput) {
            String input = promptMethod("""
                    Will you be financing?
                    Type 'Y' for yes or 'N' for no:
                    """);
            if (input.equalsIgnoreCase("n")) {
                validInput = true;
            } else if (input.equalsIgnoreCase("y")) {
                isFinancing = true;
                validInput = true;
            } else {
                System.out.println("Please enter a valid option.");
            }
        }
        return isFinancing;
    }

    // Prompt method for dry code
    static public String promptMethod(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

}




