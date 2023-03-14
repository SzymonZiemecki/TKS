package com.tks.security;

import com.tks.Product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepositoryPort extends Repository<Product> {
    List<Product> findByName(String name);
    List<Product> findByProducer(String producer);
    boolean isInOngoingOrder(UUID productId);
}
