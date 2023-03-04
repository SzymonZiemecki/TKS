package com.tks.infrastructure.orders;

import java.util.UUID;

public interface UpdateOrderPort {
    void deliverOrder(UUID orderId);
}
