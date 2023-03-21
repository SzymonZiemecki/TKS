package com.tks.aggregates;

import com.tks.Product.Product;
import com.tks.security.ProductRepositoryPort;
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
        return productEntRepository.findById(UUID.fromString(id)).isPresent();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(ProductEnt.productEntToDomainModel(productEntRepository.findById(id).get()));

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


