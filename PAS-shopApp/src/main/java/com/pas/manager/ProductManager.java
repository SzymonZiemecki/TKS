package com.pas.manager;

import com.pas.model.Product.Product;
import com.pas.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductManager {

    @Inject
    private ProductRepository productRepository;

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByProducer(String producer) {
        return productRepository.findByProducer(producer);
    }

    public Product addItem(Product product){
        return productRepository.add(product);
    }

    public Product updateProduct(UUID id, Product product){
        return productRepository.update(id, product);
    }
    public void removeItem(UUID id) {
        productRepository.delete(id);
    }
}
