package com.tks.userinterface;

import com.tks.Product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Named;

public interface ProductService {
    Product findById(UUID id);
    List<Product> findAllProducts();
    List<Product> findByName(String name);
    List<Product> findByProducer(String producer);
    Product addItem(Product product);
    Product updateProduct(UUID id, Product product);
    void removeItem(UUID id);
    boolean isInOngoingOrder(UUID productId);
    List<Product> getProducts(Optional<String> producer, Optional<String> name);


}
