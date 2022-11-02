package com.pas.repository;

import com.pas.model.Cart;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CartRepository extends IRepositoryImpl<Cart> {
}
