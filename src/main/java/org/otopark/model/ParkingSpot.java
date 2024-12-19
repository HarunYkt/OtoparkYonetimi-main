package org.otopark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ParkingSpot {

    private int id;                 // Park alanı ID'si
    private int spotNumber;         // Park alanı numarası (1-50 gibi)
    private boolean isOccupied;     // Park alanı dolu mu?
    private String vehiclePlate;    // Bu alanda park eden aracın plaka numarası
    private double hourlyRate;      // Saatlik ücret (dinamik olabilir)

  
}