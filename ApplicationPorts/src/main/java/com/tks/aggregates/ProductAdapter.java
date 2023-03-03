package com.tks.aggregates;

import adapters.ProductRepositoryEnt;
import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.infrastructure.products.AddProductPort;
import com.tks.infrastructure.products.DeleteProductPort;
import com.tks.infrastructure.products.GetProductPort;
import com.tks.infrastructure.products.UpdateProductPort;
import data.product.MobilePhoneEnt;

import data.product.ProductEnt;

import data.product.TvEnt;
import data.product.LaptopEnt;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductAdapter implements AddProductPort, DeleteProductPort, GetProductPort, UpdateProductPort {

    @Inject
    private ProductRepositoryEnt productRepositoryEnt;

    @Override
    @SneakyThrows
    public Product addItem(Product product) {

        return ProductEnt.ToProductDomainModel(productRepositoryEnt.add(ProductEnt.ToProductEnt(product)));
//        return null;

    }

    @Override
    public void removeItem(UUID id) {

    }

    @Override
    public Product findById(UUID id) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public List<Product> findByProducer(String producer) {
        return null;
    }

    @Override
    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        return null;
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return null;
    }
}


