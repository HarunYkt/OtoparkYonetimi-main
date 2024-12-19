package org.otopark.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.otopark.model.ParkingSpot;
import org.otopark.utils.DatabaseConnection;

public class ParkingDao {

    private Connection connection;

    public ParkingDao() throws SQLException {
        this.connection=DatabaseConnection.getInstance().getConnection();
    }

    public boolean createSpot(ParkingSpot parkingSpot) {
        String query = "INSERT INTO ParkingSpot (id, spotNumber, isOccupied, vehiclePlate, hourlyRate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, parkingSpot.getId());
            stmt.setInt(2, parkingSpot.getSpotNumber());
            stmt.setBoolean(3, parkingSpot.isOccupied());
            stmt.setString(4, parkingSpot.getVehiclePlate());
            stmt.setDouble(5, parkingSpot.getHourlyRate());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

public List<ParkingSpot> getAllParkingSpots() {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        String query = "SELECT * FROM ParkingSpot ";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int spotNumber = rs.getInt("spotNumber");
                boolean isOccupied = rs.getBoolean("isOccupied");
                String vehiclePlate = rs.getString("vehiclePlate");
                double hourlyRate = rs.getDouble("hourlyRate");

                parkingSpots.add(new ParkingSpot(id,spotNumber,isOccupied,vehiclePlate,hourlyRate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parkingSpots;

  }

}
