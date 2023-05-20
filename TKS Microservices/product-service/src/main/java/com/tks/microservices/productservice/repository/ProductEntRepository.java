package com.tks.microservices.productservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.tks.microservices.productservice.repository.api.ProductRepository;
import com.tks.microservices.productservice.repository.model.LaptopEnt;
import com.tks.microservices.productservice.repository.model.MobilePhoneEnt;
import com.tks.microservices.productservice.repository.model.ProductEnt;
import com.tks.microservices.productservice.repository.model.TvEnt;

import jakarta.annotation.PostConstruct;

@Component
public class ProductEntRepository extends RepositoryImpl<ProductEnt> implements ProductRepository {
    public List<ProductEnt> findByName(String name){
        return filter(product -> product.getName().equals(name));
    }

    public List<ProductEnt> findByProducer(String make){
        return filter(product -> product.getProducer().equals(make));
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return false;
    }

    @PostConstruct
    public void init(){
        this.add(new TvEnt(100, 10D, "Tv", "Samsung", "Best Tv", "big","bigres","144hz","lcd"));
        this.add(new LaptopEnt(100, 10D, "TvA", "Samsung", "Best Laptop", "inteli7",3,"duzy","duza", 1,1, "lcd"));
        this.add(new MobilePhoneEnt(100, 10D, "TvE", "Samsung", "Best Tv", "inteli7",3,"duzy", "duza", 1,1,"lcd","android",false, false));
    }
}
