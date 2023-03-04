package com.tks.infrastructure.orders;

import com.tks.model.Order;

import java.util.UUID;

public interface UpdateOrderPort {
    void updateOrder(UUID orderId, Order order);
}
