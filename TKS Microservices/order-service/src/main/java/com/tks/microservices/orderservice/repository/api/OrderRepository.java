package com.tks.microservices.orderservice.repository.api;

import java.util.List;

import com.tks.microservices.orderservice.port.security.Repository;
import com.tks.microservices.orderservice.repository.model.OrderEnt;

public interface OrderRepository extends Repository<OrderEnt> {
    List<OrderEnt> findOngoingOrders();

    List<OrderEnt> findFinishedOrders();
}
