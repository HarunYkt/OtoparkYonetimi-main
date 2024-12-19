package org.otopark.service;

import java.sql.SQLException;
import java.util.List;

import org.otopark.dao.ParkingDao;
import org.otopark.model.ParkingSpot;

public class ParkingSpotService {

private ParkingDao parkingDao;

public ParkingSpotService() throws SQLException {
    this.parkingDao = new ParkingDao(); 
}

public boolean createSpot(ParkingSpot parkingSpot) {
    return parkingDao.createSpot(parkingSpot);
 }

 public List<ParkingSpot> getAllParkingSpots() {
    return parkingDao.getAllParkingSpots();
 }
}
