package com.tks.infrastructure.orders;

import com.tks.model.Address;
import com.tks.model.Order;

import java.util.UUID;

public interface AddOrderPort {
    Order createOrder(UUID userId, Address shippingAddress);
}
