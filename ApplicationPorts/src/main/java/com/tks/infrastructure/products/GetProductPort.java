package com.tks.infrastructure.products;


import com.tks.Product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetProductPort {
    Product findById(UUID id);
    List<Product> findAllProducts();
    List<Product> findByName(String name);
    List<Product> findByProducer(String producer);
    List<Product> getProducts(Optional<String> producer, Optional<String> name);

    boolean isInOngoingOrder(UUID productId);

}
