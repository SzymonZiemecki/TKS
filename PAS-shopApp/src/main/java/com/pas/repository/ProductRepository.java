package com.pas.repository;

import com.pas.model.Product.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository extends IRepositoryImpl<Product> {
    public List<Product> findByName(String name){
        return filter(product -> product.getName().equals(name));
    }

    public List<Product> findByProducer(String make){
        return filter(product -> product.getProducer().equals(make));
    }

}
