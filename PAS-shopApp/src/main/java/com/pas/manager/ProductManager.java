package com.pas.manager;

import com.pas.model.Product;
import com.pas.model.User;
import com.pas.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductManager {

    @Inject
    private ProductRepository productRepository;

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Optional<Product> findByMake(String make) {
        return productRepository.findByMake(make);
    }

    public Product addItem(Product product){
        return productRepository.add(product);
    }

    public void removeItem(UUID id) {
        productRepository.delete(id);
    }
}
