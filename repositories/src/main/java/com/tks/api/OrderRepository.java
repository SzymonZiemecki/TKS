package com.tks.api;

import com.tks.data.model.OrderEnt;
import com.tks.security.Repository;

import java.util.List;

public interface OrderRepository extends Repository<OrderEnt> {
    List<OrderEnt> findOngoingOrders();

    List<OrderEnt> findFinishedOrders();
}
