package com.tks.data.model;

import java.util.Date;
import java.util.List;

import com.tks.data.user.UserEnt;
import com.tks.data.user.CartItemEnt;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderEnt extends IdTraitEnt {
    private UserEnt customer;
    private AddressEnt address;
    private List<CartItemEnt> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;
    private Date deliveryDate;
    private Double price;
}
