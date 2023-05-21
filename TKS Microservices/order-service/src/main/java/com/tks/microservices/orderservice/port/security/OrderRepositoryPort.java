package com.tks.microservices.orderservice.port.security;

import java.util.List;

import com.tks.microservices.orderservice.core.model.Order;

public interface OrderRepositoryPort extends Repository<Order> {
    List<Order> findOngoingOrders();

    List<Order> findFinishedOrders();
}
