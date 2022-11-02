package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Address extends IdTrait {
    private UUID id;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;
}
