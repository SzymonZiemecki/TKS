package com.tks.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address{
    @NotNull
    @Size(min = 2, max = 20)
    private String country;
    @NotNull
    @Size(min = 2, max = 20)
    private String city;
    @NotNull
    @Size(min = 2, max = 20)
    private String street;
    @NotNull
    @Size(min = 1, max = 10)
    private String houseNumber;
    @NotNull
    @Size(min = 6, max = 10)
    private String zipCode;
}
