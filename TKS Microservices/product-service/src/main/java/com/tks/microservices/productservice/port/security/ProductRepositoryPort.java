package com.tks.microservices.productservice.port.security;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.productservice.core.model.Product;

public interface ProductRepositoryPort extends Repository<Product> {
    List<Product> findByName(String name);
    List<Product> findByProducer(String producer);
    boolean isInOngoingOrder(UUID productId);
}
