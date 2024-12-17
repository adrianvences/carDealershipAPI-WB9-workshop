package com.pluralsight.dealership.dao.vehicles;

import com.pluralsight.dealership.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// responsible for interacting with vehicles and inventory tables
@Component
public class VehicleDaoMySqlImpl implements VehicleDao {

    private DataSource dataSource;

    @Autowired
    public VehicleDaoMySqlImpl(DataSource dataSource) {this.dataSource = dataSource;};

    @Override
    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement findAllVehicles = connection.prepareStatement("""
                    SELECT make,model,year,color,odometer,price,vin,vehicleType, sold
                    FROM vehicles
                    ORDER BY price;
                    """);
            ResultSet rs = findAllVehicles.executeQuery();
            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");


                vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> findAllAvailableVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement findAllVehicles = connection.prepareStatement("""
                    SELECT make,model,year,color,odometer,price,vin,vehicleType, sold
                    FROM vehicles
                    ORDER BY price;
                    """);
            ResultSet rs = findAllVehicles.executeQuery();
            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");

                if(!sold) {
                    vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold));
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> findVehiclesByPriceRange(double minPrice, double maxPrice) {

        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT * 
                FROM vehicles
                WHERE price BETWEEN ? AND ?
                """;

        try(Connection c = dataSource.getConnection()){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price,sold));
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT * FROM vehicles WHERE make = ? AND model = ?
                ORDER BY price;
                """;

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement findByMakeModel = connection.prepareStatement(query);
            findByMakeModel.setString(1,make);
            findByMakeModel.setString(2,model);
            ResultSet rs = findByMakeModel.executeQuery();
            while(rs.next()) {
                String thisMake = rs.getString("make");
                String thisModel = rs.getString("model");
                int year = rs.getInt("year");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, thisMake, thisModel, vehicleType, color, odometer, price,sold));
                }
            }



        }catch (SQLException e) {
            throw new RuntimeException();
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> findVehiclesByYear(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT * FROM vehicles WHERE year BETWEEN ? AND ?
                ORDER BY year desc;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findByYear = connection.prepareStatement(query);
            findByYear.setInt(1,minYear);
            findByYear.setInt(2,maxYear);
            ResultSet rs = findByYear.executeQuery();

            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String color = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price,sold));
                }
            }

        }catch(SQLException e) {
            throw new RuntimeException();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                Select * FROM vehicles
                WHERE color = ?
                ORDER BY price;
                """;

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findByColor = connection.prepareStatement(query);
            findByColor.setString(1,color);
            ResultSet rs = findByColor.executeQuery();

            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String thisColor = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, make, model, vehicleType, thisColor, odometer, price,sold));
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException();
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByMileRange(int minOdom, int maxOdom) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                Select * FROM vehicles 
                WHERE odometer BETWEEN ? AND ?
                ORDER by odometer;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findByMiles = connection.prepareStatement(query);
            findByMiles.setInt(1,minOdom);
            findByMiles.setInt(2,maxOdom);
            ResultSet rs = findByMiles.executeQuery();

            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String thisColor = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String vehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, make, model, vehicleType, thisColor, odometer, price, false));
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException();
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> findVehicleByVehicleType(String vehicleType) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
                SELECT * FROM vehicles 
                WHERE vehicleType = ?;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findByType = connection.prepareStatement(query);
            findByType.setString(1,vehicleType);
            ResultSet rs = findByType.executeQuery();

            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String thisColor = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String vin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String thisVehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicles.add(new Vehicle(vin, year, make, model, thisVehicleType, thisColor, odometer, price, false));
                }
            }


        }catch (SQLException e){
            throw new RuntimeException();
        }
        return vehicles;
    }

    @Override
    public Vehicle findVehicleByVIN(String vin) {
        Vehicle vehicle = new Vehicle();
        String query = """
                SELECT * FROM vehicles
                WHERE vin = ?;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement getByVin = connection.prepareStatement(query);
            getByVin.setString(1, vin);
            ResultSet rs = getByVin.executeQuery();

            while(rs.next()) {
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String thisColor = rs.getString("color");
                int odometer = rs.getInt("odometer");
                double price = rs.getDouble("price");
                String thisVin = rs.getString("vin");
                boolean sold = rs.getBoolean("sold");
                String thisVehicleType = rs.getString("vehicleType");
                if(!sold){
                    vehicle = (new Vehicle(thisVin, year, make, model, thisVehicleType, thisColor, odometer, price, false));
                }
            }


        } catch (SQLException e ){
             e.printStackTrace();
             throw new RuntimeException();
        }
        return vehicle;
    }

    @Override
    public void editVehicle(Vehicle vehicle, String vin) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE vehicles
                    SET vin=?, year=?, make=?, model=?, vehicleType=?, color=?, odometer=?, price=?, sold=0
                    WHERE vin = ?;
                    """);
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getVehicleType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setString(9, vin);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
           e.printStackTrace(); throw new RuntimeException(e);
        }
    }


    @Override
    public void removeVehicleByVIN(String vin) {

        String query = """
                DELETE vehicles
                FROM vehicles
                WHERE vin = ?;
                """;
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleteByVIN = connection.prepareStatement(query);
            deleteByVIN.setString(1,vin);
            deleteByVIN.executeUpdate();
            System.out.println("Vehicle deleted");
            findAllVehicles();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String query = """
                INSERT INTO vehicles (vin,year,make, model, vehicleType, color, odometer, price, sold)
                VALUE (?, ?, ?, ?, ?, ?, ?, ?, 0);
                """;

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement addVehicle = connection.prepareStatement(query);
            addVehicle.setString(1, vehicle.getVin());
            addVehicle.setInt(2, vehicle.getYear());
            addVehicle.setString(3, vehicle.getMake());
            addVehicle.setString(4, vehicle.getModel());
            addVehicle.setString(5, vehicle.getVehicleType());
            addVehicle.setString(6, vehicle.getColor());
            addVehicle.setInt(7, vehicle.getOdometer());
            addVehicle.setDouble(8, vehicle.getPrice());

            addVehicle.executeUpdate();

        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }




}
