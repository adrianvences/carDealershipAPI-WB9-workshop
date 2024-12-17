package com.pluralsight.dealership.dao.dealership;

import com.pluralsight.dealership.model.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

 // DealershipDaoMySqlImpl implements DealershipDao
 // meaning that this class implements the logic for the methods from the interface

public class DealershipDaoMySqlImpl implements DealershipDao {

    private final DataSource dataSource;

    // constructor takes a dataSource object as a parameter
    // dataSource object provides connection to the database

    public DealershipDaoMySqlImpl(DataSource dataSource) {this.dataSource = dataSource;}

    @Override
    public Dealership findDealershipById(int id) {
        String name = "";
        String phone = "";
        String address = "";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * 
                    FROM dealerships
                    WHERE dealership_id = ?;
                    """);

            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                address = rs.getString("address");
                phone = rs.getString("phone");
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return new Dealership(id,name,phone,address);
    }

    @Override
    public List<Dealership> findAllDealerships() {
        return List.of();
    }


}
