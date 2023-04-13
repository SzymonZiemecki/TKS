package com.tks.data.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @SneakyThrows
    public static OrderEnt orderToEnt(Order order) {
        OrderEnt orderEnt = new OrderEnt();
        orderEnt.setId(order.getId());
        orderEnt.setCustomer(UserEnt.userToEnt(order.getCustomer()));
        orderEnt.setAddress(AddressEnt.addressToEntModel(order.getAddress()));
        orderEnt.setCreationDate(order.getCreationDate());
        orderEnt.setItems(order.getItems().stream().map(CartItemEnt::cartItemToEnt).collect(Collectors.toList()));
        orderEnt.setPaid(order.isPaid());
        orderEnt.setDiscountPercent(order.getDiscountPercent());
        orderEnt.setDelivered(order.isDelivered());
        orderEnt.setPrice(order.getPrice());
        return orderEnt;
    }

    @SneakyThrows
    public static Order orderEntToDomainModel(OrderEnt orderEnt) {
        Order order = new Order();
        order.setId(orderEnt.getId());
        order.setCustomer(UserEnt.userEntToDomainModel(orderEnt.getCustomer()));
        order.setAddress(AddressEnt.addressEntToDomainModel(orderEnt.getAddress()));
        order.setItems(orderEnt.getItems().stream().map(CartItemEnt::cartItemEntToDomainModel).collect(Collectors.toList()));
        order.setCreationDate(orderEnt.getCreationDate());
        order.setPaid(orderEnt.isPaid());
        order.setDiscountPercent(order.getDiscountPercent());
        order.setDelivered(orderEnt.isDelivered());
        order.setPrice(orderEnt.getPrice());
        return order;
    }
}
