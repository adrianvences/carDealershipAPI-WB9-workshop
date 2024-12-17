package com.pluralsight.dealership.view;

import com.pluralsight.dealership.dao.contracts.LeaseDaoMySqlImpl;
import com.pluralsight.dealership.dao.contracts.SalesDaoMySqlImpl;
import com.pluralsight.dealership.dao.vehicles.VehicleDaoMySqlImpl;
import com.pluralsight.dealership.datamanagers.ContractFileManager;
import com.pluralsight.dealership.model.Contract;
import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.SalesContract;
import com.pluralsight.dealership.model.Vehicle;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

public class UserInterface {


    static Scanner scanner = new Scanner(System.in);
    private VehicleDaoMySqlImpl vehicleDB;
    private SalesDaoMySqlImpl salesVehicleDB;
    private LeaseDaoMySqlImpl leaseVehicleDB;

    public UserInterface(DataSource dataSource) {
        this.vehicleDB = new VehicleDaoMySqlImpl(dataSource);
        this.salesVehicleDB = new SalesDaoMySqlImpl(dataSource);
        this.leaseVehicleDB = new LeaseDaoMySqlImpl(dataSource);
    }

    // Display method to show menu and load init() method
    public void display() {
        String choice;
        do {
            displayDealershipMenu();
            choice = usersMenuInput(scanner);

            // switch statement to process users choice
            switch (choice.toLowerCase()) {
                case "1":
                    processGetAllVehicles();
                    break;
                case "2":
                    processGetByPriceRequest();
                    break;
                case "3":
                 processGetByMakeModelRequest();
                    break;
                case "4":
                 processGetByColorRequest();
                    break;
                case "5":
                 processGetByMileageRequest();
                    break;
                case "6":
                 processGetByVehicleTypeRequest();
                    break;
                case "7":
                 processGetByYearRequest();
                    break;
                case "8":
                 processAddVehicleRequest();
                    break;
                case "9":
                 processRemoveVehicleRequest();
                    break;
                case "10":
                    displaySellLeaseMenu();
                    break;
                case "x":
                    System.out.println("Quit");
                    break;
                default:
                    System.out.println("Invalid input try again.");
            }
            // stops loop once "x" is input
        } while (!choice.equalsIgnoreCase("x"));
        scanner.close();
    }

    // input method for switch statement
    private String usersMenuInput(Scanner scanner) {
        System.out.print("Enter your command: ");
        return scanner.nextLine().trim();
    }

    // Prompt method for dry code
    public String promptMethod(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    // dealership display prompt
    private void displayDealershipMenu() {
        System.out.println("""
                ~ Please enter the number or letter corresponding to your choice:
                1 ) List all Vehicles
                2 ) Find Vehicles Within a Price Range
                3 ) Find Vehicles by make / model
                4 ) Find vehicles by color
                5 ) Find vehicles by mileage range
                6 ) Find vehicles by type (car, truck, SUV, van)
                7 ) Find vehicles by year range
                8 ) Add a vehicle
                9 ) Remove a vehicle
                10) Sell/Lease a Vehicle
                x ) Quit
                """);
    }

    private void displaySellLeaseMenu() {

        boolean loopFlag = true;

        while (loopFlag) {

            String input = promptMethod("""
                    ~ Please enter the number or letter corresponding to your choice:
                    1 ) Sell Vehicle
                    2 ) Lease Vehicle
                    x ) Back to menu
                    """);
            switch (input.toLowerCase()) {
                case "1":
                    processSellVehicleRequest();
                    break;
                case "2":
                    processLeaseVehicleRequest();
                    break;
                case "x":
                    System.out.println("Back to home screen ...");
                    loopFlag = false;
                    break;
                default:
                    System.out.println("invalid input");
            }

        }
    }

        public void processSellVehicleRequest(){
        String contractType = "saleContract";
        Vehicle vehicle = processGetByVinRequest();
        Contract c = ContractFileManager.makeContract(contractType,vehicle);
        SalesContract salesContract = (SalesContract) c;
        salesVehicleDB.saveSalesDao(salesContract);
            System.out.println("sale saved");
    }


    public void processLeaseVehicleRequest(){
        String contractType = "leaseContract";
        Vehicle vehicle = processGetByVinRequest();
        if(!ContractFileManager.okayToLeaseMethod(vehicle.getYear())){
            System.out.println("Vehicle is not available for lease.");
            return;
        }
        Contract c = ContractFileManager.makeContract(contractType,vehicle);
        LeaseContract leaseContract = (LeaseContract) c;
        leaseVehicleDB.saveLeaseContract(leaseContract);
    }

    // method to print out inventory
    private void processGetAllVehicles() {
        displayVehicles(vehicleDB.findAllVehicles());
    }


    public Vehicle processGetByVinRequest(){
        String vin = promptMethod("Enter Vehicle vin");
        Vehicle vehicle = vehicleDB.findVehicleByVIN(vin);
        System.out.println(vehicle);
        return vehicle;
    }

    // Method to get vehicles by price range
    public void processGetByPriceRequest() {
        int min = Integer.parseInt(promptMethod("Enter minimum Value"));
        int max = Integer.parseInt(promptMethod("Enter maximum Value"));

        List<Vehicle> vehicles = vehicleDB.findVehiclesByPriceRange(min, max);
        displayVehicles(vehicles);
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }


    // Method to get vehicles by make / model
    public void processGetByMakeModelRequest() {
        String userMakeQuery = promptMethod("What car make are you looking for?");
        String userModelQuery = promptMethod("What car model are you looking for?");
        List<Vehicle> vehicles = vehicleDB.findVehiclesByMakeModel(userMakeQuery, userModelQuery);
        displayVehicles(vehicles);
    }

    // Method to get vehicle by year
    public void processGetByYearRequest(){
        int min = Integer.parseInt(promptMethod("Enter minimum year"));
        int max = Integer.parseInt(promptMethod("Enter maximum year"));

        List<Vehicle> vehicles = vehicleDB.findVehiclesByYear(min,max);
        displayVehicles(vehicles);
    }

    // Method to get vehicle by color
    public void processGetByColorRequest(){
        String colorQuery = promptMethod("Enter vehicle color.");
        List<Vehicle> vehicles = vehicleDB.findVehicleByColor(colorQuery);
        displayVehicles(vehicles);
    }

    // method to get vehicle by mileage (odometer)
    public void processGetByMileageRequest(){
        int min = Integer.parseInt(promptMethod("Enter minimum mileage"));
        int max = Integer.parseInt(promptMethod("Enter maximum mileage"));

        List<Vehicle> vehicles = vehicleDB.findVehicleByMileRange(min,max);
        displayVehicles(vehicles);
    }

    // Method to get vehicle by type
    public void processGetByVehicleTypeRequest(){
        String vehicleTypeQuery = promptMethod(" Enter vehicle by type (car, truck, SUV, van)");
        List<Vehicle> vehicles = vehicleDB.findVehicleByVehicleType(vehicleTypeQuery);
        displayVehicles(vehicles);
    }


    // add vehicles method
    public void processAddVehicleRequest(){
        Vehicle vehicle = displayAddVehiclePrompt();
        vehicleDB.addVehicle(vehicle);
    }

    // remove vehicle method
    public void processRemoveVehicleRequest(){
        String vinQueryToDelete = promptMethod("Insert VIN number for vehicle that you want to delete.");
        vehicleDB.removeVehicleByVIN(vinQueryToDelete);
    }

//     add vehicle prompt to display prompts and create vehicle object out of data
    public Vehicle displayAddVehiclePrompt(){
        String vehicleVin = promptMethod("Enter Vehicle VIN.");
        String vehicleYear = promptMethod("Enter Vehicle Year.");
        String vehicleMake = promptMethod("Enter Vehicle Make.");
        String vehicleModel = promptMethod("Enter Vehicle Model.");
        String vehicleType = promptMethod("Enter Vehicle Type.");
        String vehicleColor = promptMethod("Enter Vehicle Color.");
        String vehicleOdometer = promptMethod("Enter Vehicle Mileage.");
        String vehiclePrice = promptMethod("Enter Vehicle Price.");
        boolean vehicleSold = false;


        int vehicleYearInt = Integer.parseInt(vehicleYear);
        int vehicleOdometerInt = Integer.parseInt(vehicleOdometer);
        int vehiclePriceInt = Integer.parseInt(vehiclePrice);


        return new Vehicle(vehicleVin,vehicleYearInt,vehicleMake,vehicleModel,vehicleType,vehicleColor,vehicleOdometerInt,vehiclePriceInt,vehicleSold);
   }
}




