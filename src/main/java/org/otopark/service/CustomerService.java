package org.otopark.service;

import java.sql.SQLException;
import java.util.*;
import org.otopark.dao.CustomerDao;
import org.otopark.model.Customer;
import org.otopark.model.ParkingSpot;
import org.otopark.utils.DatabaseConnection;

public class CustomerService {


    
    private CustomerDao customerDao;
    
    public CustomerService() throws SQLException {
        customerDao = new CustomerDao();
    }


    public boolean createCustomer(Customer customer){
        return customerDao.createCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }


    public boolean deleteCustomerById(int customerId) {
       return customerDao.deleteCustomerById(customerId); 
    }

    public Customer getCustomerByPhone(String phoneNumber){
        return customerDao.getCustomerByPhone(phoneNumber);
    }

    public ParkingSpot getParkingSpotBySpotNumber(int spotNumber) throws SQLException {
        return customerDao.getParkingSpotBySpotNumber(spotNumber);
    }
}
