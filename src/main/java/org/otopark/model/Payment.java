package org.otopark.model;

import lombok.Data;

import java.util.Date;

@Data
public class Payment {

    private int id;                     
    private double amount;              
    private Date paymentTime;           
    private String paymentMethod;       
    private int parkingSpotId;


    public Payment(){}
    
    public Payment(int id , double amount, Date paymentTime, String paymentMethod, int parkingSpotId) {
        this.id = id;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.parkingSpotId = parkingSpotId;
    }   
    
}