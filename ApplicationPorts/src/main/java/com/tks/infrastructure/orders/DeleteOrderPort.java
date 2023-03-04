package com.tks.infrastructure.orders;

import java.util.UUID;

public interface DeleteOrderPort {
    void cancelOrder(UUID orderId);

    void deleteOrder(UUID orderId);
}
