package com.pas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Order extends IdTrait {
    @JsonProperty
    private User customer;
    @JsonProperty
    private Address address;
    @JsonProperty
    private Map<Product, Long> items;
    @JsonProperty
    private Date creationDate;
    @JsonProperty
    private boolean isPaid;
    @JsonProperty
    private double discountPercent;
    @JsonProperty
    private boolean isDelivered;
    @JsonProperty
    private Date deliveryDate;
    @JsonProperty
    private Double price;

    public Order(User customer, Address address, Map<Product, Long> items, Date creationDate, boolean isPaid, double discountPercent, boolean isDelivered, Double price) {
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
