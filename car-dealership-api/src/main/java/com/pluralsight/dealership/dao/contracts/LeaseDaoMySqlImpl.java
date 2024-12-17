package com.pluralsight.dealership.dao.contracts;

import com.pluralsight.dealership.model.LeaseContract;
import com.pluralsight.dealership.model.SalesContract;
import com.pluralsight.dealership.model.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
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

    @Override
    public LeaseContract findLeaseById(int id) {
        String query = """
                SELECT * FROM lease_contracts
                    JOIN vehicles ON vehicles.vin = lease_contracts.vin
                    WHERE id = ?;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findLeaseById = connection.prepareStatement(query);
            findLeaseById.setInt(1,id);
            ResultSet rs = findLeaseById.executeQuery();

            if(rs.next()) {
                System.out.println("res");
                String sale_date = rs.getString("lease_start_date");
                String customer_name = rs.getString("customer_name");
                String customer_email = rs.getString("customer_address");
                String vin = rs.getString("vin");
                int year = rs.getInt("year");
                String make = rs.getString("make");
                String model = rs.getString("model");
                String vehicleType = rs.getString("vehicleType");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                boolean sold = false;
                System.out.println("1");
                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
                System.out.println("2");
                LeaseContract contract = new LeaseContract(sale_date, customer_name, customer_email, vehicle, vehicle.getPrice());
                System.out.println(contract);
                return contract;
            }else{
                System.out.println("no contract");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace(); throw new RuntimeException();
        }
    }
}
