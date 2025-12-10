package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        // TODO: Implement the logic to add a sales contract
        String insertDataQuery = "INSERT INTO sales_contracts (VIN, sale_date, price) " +
                "VALUES (?, ?,  ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the insert query.
            insertStatement.setString(1, salesContract.getVin());
            insertStatement.setDate(2, java.sql.Date.valueOf(salesContract.getSaleDate()));
            insertStatement.setDouble(3, salesContract.getPrice());

            int affectedRows = insertStatement.executeUpdate(); // Execute the insert query.

            if (affectedRows == 0) {
                throw new SQLException("Failed to add contract, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }
}
