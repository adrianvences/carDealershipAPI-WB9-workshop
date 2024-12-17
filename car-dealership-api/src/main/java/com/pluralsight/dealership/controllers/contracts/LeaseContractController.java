package com.pluralsight.dealership.controllers.contracts;

import com.pluralsight.dealership.dao.contracts.LeaseDao;
import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LeaseContractController {

    private LeaseDao leaseDao;

    @Autowired
    public LeaseContractController(LeaseDao leaseDao) {
        this.leaseDao = leaseDao;
    }

    @GetMapping("/lease/{id}")
    public LeaseContract getLeaseById(@PathVariable("id") int id) {
        return leaseDao.findLeaseById( id );
    }

    @PostMapping("/lease")
    @ResponseStatus(code= HttpStatus.CREATED)
    public void postLeaseContract(@RequestBody LeaseContract leaseContract){
        leaseDao.saveLeaseContract(leaseContract);
    }
}
