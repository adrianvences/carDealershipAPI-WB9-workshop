package com.pluralsight.dealership.controllers.vehicle;

import com.pluralsight.dealership.dao.vehicles.VehicleDao;
import com.pluralsight.dealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

//    localhost:8080/api/vehicles
    @RequestMapping(path="/vehicles", method = RequestMethod.GET)
    public List<Vehicle> getVehicles(){
        return vehicleDao.findAllVehicles();
    }

//    http://localhost:8080/api/vehiclePrice?minPrice=20000&maxPrice=50000
    @GetMapping("/vehiclePrice")
    public List<Vehicle> getVehiclesByPriceRange(@RequestParam("minPrice") double minPrice, @RequestParam("maxPrice") double maxPrice){
        return vehicleDao.findVehiclesByPriceRange(minPrice, maxPrice);
    }

//    http://localhost:8080/api/vehicleByMakeModel?make=ford&model=f150
    @GetMapping("/vehicleByMakeModel")
    public List<Vehicle> getVehiclesByMakeModel(@RequestParam("make") String make, @RequestParam("model") String model){
        return vehicleDao.findVehiclesByMakeModel(make, model);
    }

//    http://localhost:8080/api/vehicleByYear?minYear=1999&maxYear=2020
    @GetMapping("/vehicleByYear")
    public List<Vehicle> getVehiclesYearRange(@RequestParam("minYear") int minYear, @RequestParam("maxYear") int maxYear){
        return vehicleDao.findVehiclesByYear(minYear,maxYear);
    }

//    http://localhost:8080/api/vehicleByColor?color=white
    @GetMapping("/vehicleByColor")
    public List<Vehicle> getVehiclesByColor(@RequestParam("color") String color){
        return vehicleDao.findVehicleByColor(color);
    }

//    http://localhost:8080/api/vehicleByMileRange?minMiles=50000&maxMiles=150000
    @GetMapping("/vehicleByMileRange")
    public List<Vehicle> getVehiclesByOdometer(@RequestParam("minMiles") int minMiles, @RequestParam("maxMiles") int maxMiles){
        return vehicleDao.findVehicleByMileRange(minMiles,maxMiles);
    }

//    http://localhost:8080/api/vehicleByType?vehicleType=truck
    @GetMapping("/vehicleByType")
    public List<Vehicle> getVehiclesByType(@RequestParam("vehicleType") String vehicleType){
        return vehicleDao.findVehicleByVehicleType(vehicleType);
    }

    @PostMapping("/addVehicle")
    @ResponseStatus(code= HttpStatus.CREATED)
    public void addVehicles(@RequestBody Vehicle vehicle) {vehicleDao.addVehicle(vehicle);}

    @PutMapping("/editVehicle/{vin}")
    public void putVehicle(@RequestBody Vehicle vehicle, @PathVariable("vin") String vin){
        vehicleDao.editVehicle(vehicle,vin);
    }

    @DeleteMapping("deleteVehicleByVin/{vin}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteVehicleByVin(@PathVariable("vin") String vin){
        vehicleDao.removeVehicleByVIN(vin);
    }





}
