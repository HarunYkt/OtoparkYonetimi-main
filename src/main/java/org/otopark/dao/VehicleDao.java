package org.otopark.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.otopark.model.Vehicle;
import org.otopark.utils.DatabaseConnection;

public class VehicleDao {

private Connection connection;

    public VehicleDao()throws SQLException {
        this.connection=DatabaseConnection.getInstance().getConnection();
    }

    public boolean createVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicle (plateNumber, vehicleType, customer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setString(2, vehicle.getVehicleType());
            stmt.setInt(3, vehicle.getOwner().getId());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicleById(int vehicleId) {
        String query = "DELETE FROM vehicle WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Vehicle> getAllVehicles() {
        
        String query = "SELECT * FROM vehicle";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            List<Vehicle> vehicles = new ArrayList<>();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setPlateNumber(rs.getString("plateNumber"));
                vehicle.setVehicleType(rs.getString("vehicleType"));
                // vehicle.setOwner(null);
                vehicles.add(vehicle);
            }
            return vehicles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    
}
