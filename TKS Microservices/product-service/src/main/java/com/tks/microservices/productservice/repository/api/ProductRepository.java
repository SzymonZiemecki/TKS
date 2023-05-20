package com.tks.microservices.productservice.repository.api;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.productservice.port.security.Repository;
import com.tks.microservices.productservice.repository.model.ProductEnt;

public interface ProductRepository extends Repository<ProductEnt> {
    List<ProductEnt> findByName(String name);
    List<ProductEnt> findByProducer(String producer);
    boolean isInOngoingOrder(UUID productId);
}
