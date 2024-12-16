package com.pluralsight.dealership.dao.contracts;

import com.pluralsight.dealership.model.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaseDaoMySqlImpl implements LeaseDao {
    private final DataSource dataSource;


    public LeaseDaoMySqlImpl(DataSource dataSource) {this.dataSource = dataSource;}


    @Override
    public void saveLeaseContract(LeaseContract leaseContract) {
        try(Connection connection = dataSource.getConnection()) {
            String query = """
                    INSERT INTO lease_contracts (VIN, lease_fee_rate, finance_rate, lease_months, lease_start_date, lease_end_date, lease_monthly_payment, original_price,expected_ending_value, customer_name, customer_address)
                    VALUES
                    (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?);
                    """;
            PreparedStatement saveLease = connection.prepareStatement(query);
            saveLease.setString(1,leaseContract.getVehicleSold().getVin());
            saveLease.setDouble(2,leaseContract.getLeaseFeeRate());
            saveLease.setDouble(3, leaseContract.getFinanceRate());
            saveLease.setInt(4,leaseContract.getLeaseMonths());
            saveLease.setString(5, "2024-12-13");
            saveLease.setString(6, "2024/12/13");
            saveLease.setDouble(7, leaseContract.getMonthlyPayment());
            saveLease.setDouble(8, leaseContract.getOriginalPrice());
            saveLease.setDouble(9, leaseContract.getExpectedEndingValue());
            saveLease.setString(10, leaseContract.getCustomerName());
            saveLease.setString(11, leaseContract.getCustomerEmail());

            saveLease.executeUpdate();
        } catch (SQLException e ){
            e.printStackTrace();
            throw new RuntimeException();
    }
}
}
