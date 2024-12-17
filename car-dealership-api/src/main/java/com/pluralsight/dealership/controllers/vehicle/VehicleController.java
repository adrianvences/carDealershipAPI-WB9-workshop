package com.pluralsight.dealership.controllers.vehicle;

import com.pluralsight.dealership.dao.vehicles.VehicleDao;
import com.pluralsight.dealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @RequestMapping(path="/vehicles", method = RequestMethod.GET)
    public List<Vehicle> getVehicles(){
        return vehicleDao.findAllVehicles();
    }

    @GetMapping("/vehiclePrice")




}
