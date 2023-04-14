package com.tks.services;

import com.tks.Product.Product;
import com.tks.security.ProductRepositoryPort;
import com.tks.userinterface.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductRepositoryPort productRepository;
    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findByProducer(String producer) {
        return productRepository.findByProducer(producer);
    }

    @Override
    public Product addItem(Product product) {
        return productRepository.add(product);
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return productRepository.update(id, product);
    }

    @Override
    public void removeItem(UUID id) {
        productRepository.delete(id);
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return productRepository.isInOngoingOrder(productId);
    }

    @Override
    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        return productRepository.findAll();
    }
}
