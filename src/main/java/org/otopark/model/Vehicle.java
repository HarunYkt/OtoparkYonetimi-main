package org.otopark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Vehicle {

    private int id;
    private String plateNumber;
    private String vehicleType;
    private Customer owner;
}
