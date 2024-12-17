package com.pluralsight.dealership.controllers.vehicle;

import com.pluralsight.dealership.dao.vehicles.VehicleDao;
import com.pluralsight.dealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
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




}
