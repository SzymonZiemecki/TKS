package com.tks.microservices.orderservice.repository.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.tks.microservices.orderservice.core.model.Order;
import com.tks.microservices.orderservice.repository.ClientEntRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderEnt extends IdTraitEnt {
    private ClientEnt client;
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
        orderEnt.setClient(ClientEnt.clientToEnt(order.getClient()));
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
        order.setClient(ClientEnt.clientEntToDomainModel(orderEnt.getClient()));
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
