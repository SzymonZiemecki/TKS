package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Order extends IdTrait {
    private UUID id;
    private User customer;
    private Address address;
    private List<Product> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
}
