package com.tks.aggregates;

import com.tks.Product.Product;
import com.tks.infrastructure.ProductRepositoryPort;
import com.tks.data.product.ProductEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.tks.repository.ProductEntRepository;

import java.util.*;
import java.util.function.Predicate;

import static com.tks.mapper.EntityModelMapper.*;

@ApplicationScoped
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    @Inject
    ProductEntRepository productEntRepository;

    @Override
    public List<Product> findByName(String name) {
        return toDomainModel(productEntRepository.findByName(name));
    }

    @Override
    public List<Product> findByProducer(String producer) {
        return toDomainModel(productEntRepository.findByProducer(producer));
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return toDomainModel(productEntRepository.isInOngoingOrder(productId));
    }

    @Override
    public Product add(Product entity) {
        return toDomainModel(productEntRepository.add(toEntModel(entity)));
    }

    @Override
    public void delete(UUID id) {
        productEntRepository.delete(id);
    }

    @Override
    public void delete(Product entity) {
        productEntRepository.delete((ProductEnt) toEntModel(entity));
    }

    @Override
    public Product update(UUID id, Product entity) {
        return toDomainModel(productEntRepository.update(id, (ProductEnt) toEntModel(entity)));
    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return toDomainModel(productEntRepository.findById(id));

    }

    @Override
    public List<Product> findAll() {
        return listToDomainModel(productEntRepository.findAll());

    }

    @Override
    public int size() {
        return productEntRepository.size();

    }
    public List<ProductEnt> filter(Predicate<ProductEnt> predicate) {
        return listToDomainModel(productEntRepository.filter(predicate));
    }
}


