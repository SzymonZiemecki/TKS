package com.tks.infrastructure.carts;

import com.tks.model.Cart;

import java.util.UUID;

public interface RemoveFromUserCartPort {
    Cart removeFromCart(UUID userId, UUID productId);
}
