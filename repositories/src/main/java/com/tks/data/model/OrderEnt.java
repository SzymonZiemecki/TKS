package com.tks.data.model;

import java.util.Date;
import java.util.List;

import com.tks.data.user.CartEnt;
import com.tks.data.user.UserEnt;
import com.tks.data.user.CartItemEnt;
import com.tks.model.Cart;
import com.tks.model.Order;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @SneakyThrows
    public static OrderEnt orderToEnt(Order order) {
        OrderEnt orderEnt = new OrderEnt();
        BeanUtils.copyProperties(orderEnt, order);

        return orderEnt;
    }

    @SneakyThrows
    public static Order orderEntToDomainModel(OrderEnt orderEnt) {
        Order order = new Order();

        BeanUtils.copyProperties(order, orderEnt);
        return order;
    }
}
