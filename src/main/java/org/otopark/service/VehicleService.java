package org.otopark.service;

import java.sql.SQLException;
import java.util.List;

import org.otopark.dao.VehicleDao;
import org.otopark.model.Vehicle;

public class VehicleService {

    private VehicleDao vehicleDao;

    public VehicleService() throws SQLException {
        vehicleDao = new VehicleDao();
    }   

    public boolean createVehicle(Vehicle vehicle) {
        return vehicleDao.createVehicle(vehicle);
    }

    public boolean deleteCustomerById(int vehicleId) {
       return vehicleDao.deleteVehicleById(vehicleId);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAllVehicles();
    }
    
}
