package com.pluralsight.dealership.dao.vehicles;

import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {

    // Phase 1 and phase 2 workshop methods
    List<Vehicle> findAllVehicles();
    List<Vehicle> findAllAvailableVehicles();
    List<Vehicle> findVehiclesByPriceRange(double min, double max);
    List<Vehicle> findVehiclesByMakeModel(String make, String model);
    List<Vehicle> findVehiclesByYear(int minYear, int maxYear);
    List<Vehicle> findVehicleByColor(String color);
    List<Vehicle> findVehicleByMileRange(int minOdom, int maxOdom);
    List<Vehicle> findVehicleByVehicleType(String vehicleType);
    Vehicle findVehicleByVIN(String vin);
    void editVehicle(Vehicle vehicle,String vin);
    void removeVehicleByVIN(String vin);
    void addVehicle(Vehicle vehicle);
}
