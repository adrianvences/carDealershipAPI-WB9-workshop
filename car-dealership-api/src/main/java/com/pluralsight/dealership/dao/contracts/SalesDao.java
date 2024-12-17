package com.pluralsight.dealership.dao.contracts;

import com.pluralsight.dealership.model.SalesContract;

public interface SalesDao {
    void saveSalesDao(SalesContract salesContract);
    SalesContract findSalesContractById(int id);
}
