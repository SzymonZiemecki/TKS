package com.tks.infrastructure;

import com.tks.model.Order;

import java.util.List;

public interface OrderRepositoryPort extends Repository<Order> {
    List<Order> findOngoingOrders();

    List<Order> findFinishedOrders();
}
