package com.pas.repository;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository extends IRepositoryImpl<Product> {
    public List<Product> findByName(String name){
        return filter(product -> product.getName().equals(name));
    }

    public List<Product> findByProducer(String make){
        return filter(product -> product.getProducer().equals(make));
    }
    @PostConstruct
    public void init(){
        this.add(new Tv(100, 10D, "Tv", "Samsung", "Best Tv", "big","bigres","144hz","lcd"));
        this.add(new Tv(100, 10D, "TvA", "Samsung", "Best Tv", "big","bigres","144hz","lcd"));
        this.add(new Tv(100, 10D, "TvE", "Samsung", "Best Tv", "big","bigres","144hz","lcd"));
    }
}
