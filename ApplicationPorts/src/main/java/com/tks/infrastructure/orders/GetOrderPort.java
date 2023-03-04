package com.tks.infrastructure.orders;

import com.tks.User.User;
import com.tks.model.Order;

import java.util.List;
import java.util.UUID;

public interface GetOrderPort {

    List<Order> getAllOrders();

    Order getOrderById(UUID id);

    List<Order> findOngoingOrders();

    List<Order> findFinishedOrders();

    User findUserInOrder(UUID orderId);
}
