package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        // TODO: Implement the logic to add a lease contract
        String insertDataQuery = "INSERT INTO sales_contracts (VIN, lease_start, lease_end, monthly_payment) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            // Setting parameters for the insert query.
            insertStatement.setString(1, leaseContract.getVin());
            insertStatement.setDate(2, java.sql.Date.valueOf(leaseContract.getLeaseStart()));
            insertStatement.setDate(3, java.sql.Date.valueOf(leaseContract.getLeaseEnd()));
            insertStatement.setDouble(4, leaseContract.getMonthlyPayment());

            int affectedRows = insertStatement.executeUpdate(); // Execute the insert query.

            if (affectedRows == 0) {
                throw new SQLException("Failed to add contract, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the SQL exception.
        }
    }
}
