package com.tks.model;


import com.tks.IdTrait;
import com.tks.User.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Order extends IdTrait {
    private User customer;
    private Address address;
    private List<CartItem> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;
    private Date deliveryDate;
    private Double price;

    public Order(User customer, Address address, List<CartItem> items, Date creationDate, boolean isPaid, double discountPercent, boolean isDelivered, Double price) {
        this.customer = customer;
        this.address = address;
        this.items = items;
        this.creationDate = creationDate;
        this.isPaid = isPaid;
        this.discountPercent = discountPercent;
        this.isDelivered = isDelivered;
        this.price = price;
    }

}
