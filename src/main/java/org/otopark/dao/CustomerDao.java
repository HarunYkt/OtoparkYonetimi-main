package org.otopark.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.otopark.model.Customer;
import org.otopark.model.ParkingSpot;
import org.otopark.utils.DatabaseConnection;

public class CustomerDao {

    private Connection connection;

    public CustomerDao() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean createCustomer(Customer customer){

     String query  = "INSERT INTO customers (name, surname, email, phone_number, age) VALUES (?, ?, ?, ?, ?)";
     try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getSurname());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setInt(5, customer.getAge());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setSurname(rs.getString("surname"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAge(rs.getInt("age"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean deleteCustomerById(int customerId) {

        String query = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerByPhone(String phoneNumber){
        String query = "SELECT * FROM customers WHERE phone_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setSurname(rs.getString("surname"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAge(rs.getInt("age"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParkingSpot getParkingSpotBySpotNumber(int spotNumber) throws SQLException {
        String query = "SELECT * FROM ParkingSpot WHERE spotNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, spotNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ParkingSpot parkingSpot = new ParkingSpot();
                    parkingSpot.setId(rs.getInt("id"));
                    parkingSpot.setSpotNumber(rs.getInt("spotNumber"));
                    parkingSpot.setOccupied(rs.getBoolean("isOccupied"));
                    parkingSpot.setVehiclePlate(rs.getString("vehiclePlate"));
                    parkingSpot.setHourlyRate(rs.getDouble("hourlyRate"));
                    return parkingSpot;
                }
            }
        }
        return null; 
    }
    
}
