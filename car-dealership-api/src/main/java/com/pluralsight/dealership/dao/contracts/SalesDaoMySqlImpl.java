package com.pluralsight.dealership.dao.contracts;

import com.pluralsight.dealership.model.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesDaoMySqlImpl implements SalesDao {
    private final DataSource dataSource;

    public SalesDaoMySqlImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void saveSalesDao(SalesContract salesContract) {
        try (Connection connection = dataSource.getConnection()) {

            String query = """
                    INSERT INTO sales_contract (sales_tax_amount, recording_fee, processing_fee, sale_price, sale_date, customer_name, customer_address, vin, financing,vehicle_price)
                    VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?, ?,?);
                    """;

            PreparedStatement saveContract = connection.prepareStatement(query);
            saveContract.setDouble(1, salesContract.getSalesTaxAmount());
            saveContract.setDouble(2, salesContract.getRecordingFee());
            saveContract.setDouble(3, salesContract.getProcessingFeeUnder10000());
            saveContract.setDouble(4, salesContract.getTotalPrice());
            saveContract.setString(5, salesContract.getDate());
            saveContract.setString(6, salesContract.getCustomerName());
            saveContract.setString(7, salesContract.getCustomerEmail());
            saveContract.setString(8, salesContract.getVehicleSold().getVin());
            saveContract.setBoolean(9, salesContract.isFinancing());
            saveContract.setDouble(10, salesContract.getVehicleSold().getPrice());

            PreparedStatement updateVehicle = connection.prepareStatement("""
                    UPDATE vehicles
                    set sold = 1
                    WHERE vin = ?;
                    """);

            updateVehicle.setString(1, salesContract.getVehicleSold().getVin());
            saveContract.executeUpdate();
            updateVehicle.executeUpdate();

        } catch (SQLException e) {
            System.out.println("vin does not exist");
            throw new RuntimeException(e);
        }

    }
}
