package com.tks.microservices.productservice.repository.aggreagates;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tks.microservices.productservice.core.model.Product;
import com.tks.microservices.productservice.port.security.ProductRepositoryPort;
import com.tks.microservices.productservice.repository.ProductEntRepository;
import com.tks.microservices.productservice.repository.model.ProductEnt;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private ProductEntRepository productEntRepository;

    public ProductRepositoryAdapter(final ProductEntRepository productEntRepository) {
        this.productEntRepository = productEntRepository;
    }

    @Override
    public List<Product> findByName(String name) {
        return productEntRepository.findByName(name)
                .stream()
                .map(ProductEnt::productEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByProducer(String producer) {
        List<ProductEnt> productEntList= productEntRepository.findByProducer(producer);
        List<Product> productList = new ArrayList<>();

        for (ProductEnt ent: productEntList) {
            productList.add(ProductEnt.productEntToDomainModel(ent));
        }

        return productList;
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return (productEntRepository.isInOngoingOrder(productId));
    }

    @Override
    public Product add(Product entity) {
        return ProductEnt.productEntToDomainModel(productEntRepository.add(ProductEnt.productToEnt(entity)));
    }

    @Override
    public void delete(UUID id) {
        productEntRepository.delete(id);
    }

    @Override
    public void delete(Product entity) {
        productEntRepository.delete(ProductEnt.productToEnt(entity));
    }

    @Override
    public Product update(UUID id, Product entity) {
        return ProductEnt.productEntToDomainModel(productEntRepository.update(id, ProductEnt.productToEnt(entity)));
    }

    @Override
    public boolean exists(String id) {
        try{
            productEntRepository.findById(UUID.fromString(id));
            return true;
        } catch (NotFoundException e){
            return false;
        }
    }

    @Override
    public Product findById(UUID id) {
        return ProductEnt.productEntToDomainModel(productEntRepository.findById(id));
    }

    @Override
    public List<Product> findAll() {
        return productEntRepository.findAll()
                .stream()
                .map(ProductEnt::productEntToDomainModel)
                .collect(Collectors.toList());

    }

    @Override
    public int size() {
        return productEntRepository.size();
    }
    public List<Product> filter(Predicate<ProductEnt> predicate) {
        return productEntRepository.filter(predicate)
                .stream()
                .map(ProductEnt::productEntToDomainModel)
                .collect(Collectors.toList());
    }
}


