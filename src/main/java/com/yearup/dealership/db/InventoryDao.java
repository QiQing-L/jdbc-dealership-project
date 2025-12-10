package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        String insertDataQuery = "INSERT INTO inventory (dealership_id, VIN) " +
                "VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the insert query.
            insertStatement.setInt(1, dealershipId);
            insertStatement.setString(2, vin);

            int affectedRows = insertStatement.executeUpdate(); // Execute the insert query.

            if (affectedRows == 0) {
                throw new SQLException("Failed to add vehicle to dealership, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }

    }

    public void removeVehicleFromInventory(String vin) {
        String deleteDataQuery = "DELETE FROM inventory WHERE VIN = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteDataQuery)) {
            deleteStatement.setString(1, vin); // Set the parameter in the delete query.
            deleteStatement.executeUpdate(); // Execute the delete query.
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }
}
