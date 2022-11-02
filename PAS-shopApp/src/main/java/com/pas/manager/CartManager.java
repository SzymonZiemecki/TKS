package com.pas.manager;

import com.pas.model.Cart;
import com.pas.repository.CartRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CartManager {

    @Inject
    private CartRepository cartRepository;

    public Optional<Cart> findById(UUID id) {
        return cartRepository.findById(id);
    }

    public Cart createCart(Cart cart){
        return cartRepository.add(cart);
    }

}