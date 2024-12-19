package org.otopark.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.otopark.model.ParkingSpot;
import org.otopark.model.Payment;
import org.otopark.utils.DatabaseConnection;

public class PaymentDao {


    private final Connection connection;

    public PaymentDao() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean makePayment(Payment payment) {
        String query = "INSERT INTO payments (amount, payment_time, payment_method, parking_spot_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, payment.getAmount());
            stmt.setTimestamp(2, new java.sql.Timestamp(payment.getPaymentTime().getTime())); // Tarihi doğru formatta ekle
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setInt(4, payment.getParkingSpotId()); // ID kullanılıyor
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getPaymentsByParkingSpot(int parkingSpotId) throws SQLException {
        String query = "SELECT * FROM payments WHERE parking_spot_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, parkingSpotId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Payment> payments = new ArrayList<>();
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setId(rs.getInt("id"));
                    payment.setAmount(rs.getDouble("amount"));
                    payment.setPaymentTime(rs.getTimestamp("payment_time")); // Timestamp dönüşümü
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setParkingSpotId(rs.getInt("parking_spot_id")); // ID ekleniyor
                    payments.add(payment);
                }
                return payments;
            }
        }
    }

    public List<Payment> getAllPayments() {
        String query = "SELECT * FROM payments";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Payment> payments = new ArrayList<>();
                while (rs.next()) {
                    Payment payment = new Payment();
                    payment.setId(rs.getInt("id"));
                    payment.setAmount(rs.getDouble("amount"));  
                    payment.setPaymentTime(rs.getTimestamp("payment_time")); // Timestamp dönüşümü
                    payment.setPaymentMethod(rs.getString("payment_method"));
                    payment.setParkingSpotId(rs.getInt("parking_spot_id")); // ID ekleniyor
                    payments.add(payment);
                }
                return payments;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public boolean deletePaymentById(int paymentId) {
       String query = "DELETE FROM payments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
