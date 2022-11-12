package com.pas.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Address extends IdTrait {
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String zipCode;
}
