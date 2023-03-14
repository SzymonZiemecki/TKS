package com.tks.api;

import com.tks.data.product.ProductEnt;
import com.tks.infrastructure.Repository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends Repository<ProductEnt> {
    List<ProductEnt> findByName(String name);
    List<ProductEnt> findByProducer(String producer);
    boolean isInOngoingOrder(UUID productId);
}
