package com.pluralsight.dealership.controllers.contracts;

import com.pluralsight.dealership.dao.contracts.SalesDao;
import com.pluralsight.dealership.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SalesContractController {
    private SalesDao salesDao;

    @Autowired
    public SalesContractController(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    @GetMapping("/sales/{id}")
    public SalesContract getSaleContractById(@PathVariable("id") int id){
        return salesDao.findSalesContractById(id);
    }

    @PostMapping("/sale")
    @ResponseStatus(code= HttpStatus.CREATED)
    public void postSalesContract(@RequestBody SalesContract salesContract){
        salesDao.saveSalesDao(salesContract);
    }

}
