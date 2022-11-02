package com.pas.repository;

import com.pas.model.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ProductRepository extends IRepositoryImpl<Product> {
    public Optional<Product> findByName(String name){
        return filter(product -> product.getName().equals(name)).stream().findFirst();
    }

    public Optional<Product> findByMake(String make){
        return filter(product -> product.getProducer().equals(make)).stream().findFirst();
    }

}
