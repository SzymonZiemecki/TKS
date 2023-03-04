package com.tks.infrastructure.carts;

import com.tks.model.Cart;

import java.util.UUID;

public interface AddToUserCartPort {
    Cart addToCart(UUID userId, UUID productId, Long quantity);
}
