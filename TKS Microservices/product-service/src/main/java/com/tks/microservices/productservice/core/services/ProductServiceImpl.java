package com.tks.microservices.productservice.core.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tks.microservices.productservice.core.model.Product;
import com.tks.microservices.productservice.port.security.ProductRepositoryPort;
import com.tks.microservices.productservice.port.ui.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepositoryPort productRepository;

    public ProductServiceImpl(final ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

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
