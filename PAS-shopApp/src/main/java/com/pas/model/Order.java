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
public class Order {
    private UUID id;
    private User customer;
    private Address address;
    private List<Product> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;

    public Order(com.pas.model.User customer, Address address, List<Product> items, Date creationDate, boolean isPaid, double discountPercent) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.address = address;
        this.items = items;
        this.creationDate = creationDate;
        this.isPaid = isPaid;
        this.discountPercent = discountPercent;
    }

}
