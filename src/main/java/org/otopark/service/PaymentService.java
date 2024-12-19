package org.otopark.service;

import java.sql.SQLException;
import java.util.List;

import org.otopark.dao.PaymentDao;
import org.otopark.model.Payment;

public class PaymentService {

    private PaymentDao paymentDao;


    public PaymentService() throws SQLException {
        paymentDao = new PaymentDao();
    }

    public boolean makePayment(Payment payment){
        return paymentDao.makePayment(payment);
    }
    public List<Payment> getPaymentsByParkingSpot(int  parkingSpotId) throws SQLException {
        return paymentDao.getPaymentsByParkingSpot(parkingSpotId);
    }

    public List<Payment> getAllPayments() {
        return paymentDao.getAllPayments();
    }

    public boolean deletePaymentById(int paymentId) {
        return paymentDao.deletePaymentById(paymentId);
    }
    
}
