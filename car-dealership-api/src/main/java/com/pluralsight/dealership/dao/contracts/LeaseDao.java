package com.pluralsight.dealership.dao.contracts;

import com.pluralsight.dealership.model.LeaseContract;

public interface LeaseDao {
    void saveLeaseContract(LeaseContract leaseContract);
}
