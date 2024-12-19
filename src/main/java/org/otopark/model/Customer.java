package org.otopark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private int age;

    public Customer(String name, String surname, String email, String phoneNumber, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }
}
