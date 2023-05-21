package com.tks.microservices.orderservice.core.model;


import java.util.Date;
import java.util.List;

import com.tks.microservices.orderservice.rest.dto.IdTrait;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Order extends IdTrait {
    private Address address;
    private Client client;
    private List<CartItem> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;
    private Date deliveryDate;
    private Double price;

    public Order(Client client, Address address, List<CartItem> items, Date creationDate, boolean isPaid, double discountPercent, boolean isDelivered, Double price) {
        this.client = client;
        this.address = address;
        this.items = items;
        this.creationDate = creationDate;
        this.isPaid = isPaid;
        this.discountPercent = discountPercent;
        this.isDelivered = isDelivered;
        this.price = price;
    }
}
