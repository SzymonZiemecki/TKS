package com.tks.userinterface;

import com.tks.model.Address;
import com.tks.model.Order;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.core.Response;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(UUID id);

    List<Order> getOngoingOrders();

    List<Order> getFinishedOrders();

    Order createOrder(UUID userId, Address shippingAddress);

    void deliverOrder(UUID id);

    void deleteOrder(UUID orderId);
}
