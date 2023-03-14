package com.tks.security;

import com.tks.model.Order;

import java.util.List;

public interface OrderRepositoryPort extends Repository<Order> {
    List<Order> findOngoingOrders();

    List<Order> findFinishedOrders();
}
