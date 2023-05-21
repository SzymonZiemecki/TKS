package com.tks.microservices.orderservice.port.ui;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.orderservice.core.model.Address;
import com.tks.microservices.orderservice.core.model.Order;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(UUID id);

    List<Order> getOngoingOrders();

    List<Order> getFinishedOrders();

    Order createOrder(UUID userId, Address shippingAddress);

    void deliverOrder(UUID id);

    void deleteOrder(UUID orderId);
}
