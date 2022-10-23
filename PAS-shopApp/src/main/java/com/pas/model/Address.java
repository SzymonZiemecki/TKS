package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private UUID id;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;

    public Address(String country, String city, String city1, String street, String houseNumber, String zipCode) {
        this.id = UUID.randomUUID();
        this.country = country;
        this.city = city;
        this.city = city1;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
    }
}
