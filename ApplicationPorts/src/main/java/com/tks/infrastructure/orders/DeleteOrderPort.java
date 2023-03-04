package com.tks.infrastructure.orders;

import java.util.UUID;

public interface DeleteOrderPort {
    void deleteOrder(UUID orderId);
}
