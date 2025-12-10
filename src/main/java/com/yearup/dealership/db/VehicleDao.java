package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String insertDataQuery = "INSERT INTO vehicles (VIN, make, model, year, sold, color, vehicleType, odometer, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the insert query.
            insertStatement.setString(1, vehicle.getVin());
            insertStatement.setString(2, vehicle.getMake());
            insertStatement.setString(3, vehicle.getModel());
            insertStatement.setInt(4, vehicle.getYear());
            insertStatement.setBoolean(5, vehicle.isSold());
            insertStatement.setString(6, vehicle.getColor());
            insertStatement.setString(7, vehicle.getVehicleType());
            insertStatement.setInt(8, vehicle.getOdometer());
            insertStatement.setDouble(9, vehicle.getPrice());
            int affectedRows = insertStatement.executeUpdate(); // Execute the insert query.

            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }

    }

    public void removeVehicle(String VIN) {

        String deleteDataQuery = "DELETE FROM vehicles WHERE VIN = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteDataQuery)) {
            deleteStatement.setString(1, VIN); // Set the ID parameter in the delete query.
            deleteStatement.executeUpdate(); // Execute the delete query.
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {

        List<Vehicle> vehicles = new ArrayList<>();
        String getByPriceQuery = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByPriceQuery)) {
            selectStatement.setDouble(1, minPrice);
            selectStatement.setDouble(2, maxPrice);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {

        List<Vehicle> vehicles = new ArrayList<>();
        String getByMakeModelQuery = "SELECT * FROM vehicles WHERE UPPER(make) = UPPER(?) AND UPPER(model) = UPPER(?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByMakeModelQuery)) {
            selectStatement.setString(1, make);
            selectStatement.setString(2, model);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {

        List<Vehicle> vehicles = new ArrayList<>();
        String getByYearQuery = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByYearQuery)) {
            selectStatement.setInt(1, minYear);
            selectStatement.setInt(2, maxYear);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {

        List<Vehicle> vehicles = new ArrayList<>();
        String getByColorQuery = "SELECT * FROM vehicles WHERE UPPER(color) = UPPER(?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByColorQuery)) {
            selectStatement.setString(1, color);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {

        List<Vehicle> vehicles = new ArrayList<>();
        String getByMileageRangeQuery = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByMileageRangeQuery)) {
            selectStatement.setInt(1, minMileage);
            selectStatement.setInt(2, maxMileage);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    public List<Vehicle> searchByType(String type) {
        // TODO:
        List<Vehicle> vehicles = new ArrayList<>();
        String getByTypeQuery = "SELECT * FROM vehicles WHERE UPPER(vehicleType) = UPPER(?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByTypeQuery)) {
            selectStatement.setString(1, type);// Set the parameter in the query.
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Extract data from the result set.

                    // Create vehicle object.
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
