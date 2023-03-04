package com.tks.aggregates;

import adapters.OrderRepository;
import adapters.ProductRepositoryEnt;

import com.tks.Product.Product;

import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.products.AddProductPort;
import com.tks.infrastructure.products.DeleteProductPort;
import com.tks.infrastructure.products.GetProductPort;
import com.tks.infrastructure.products.UpdateProductPort;



import data.model.CartItemEnt;



import data.product.MobilePhoneEnt;

import data.product.ProductEnt;

import data.product.TvEnt;
import data.product.LaptopEnt;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static data.product.ProductEnt.toProductDomainModel;
import static data.product.ProductEnt.toProductEnt;
import static data.utils.ErrorInfoEnt.ENTITY_NOT_FOUND_MESSAGE;
import static data.utils.ErrorInfoEnt.PRODUCT_IN_UNFINISHED_ORDER;

public class ProductAdapter implements AddProductPort, DeleteProductPort, GetProductPort, UpdateProductPort {

    @Inject
    private ProductRepositoryEnt productRepositoryEnt;

    @Inject
    private OrderRepository orderRepository;

    @Override
    @SneakyThrows
    public Product addItem(Product product) {


        return toProductDomainModel(productRepositoryEnt.add(toProductEnt(product)));


    }

    @Override
    public void removeItem(UUID id) {
        if (!isInOngoingOrder(id)) {
            productRepositoryEnt.delete(id);
        } else {
            throw new BusinessLogicException(PRODUCT_IN_UNFINISHED_ORDER.getValue());
        }
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return orderRepository.filter(order -> !order.isDelivered()).stream().map(order -> order.getItems()).flatMap(list -> list.stream()).map(CartItemEnt::getProduct).map(product -> product.equals(productId)).filter($ -> $.equals(true)).findAny().orElse(false);
    }


    @Override
    public Product findById(UUID id) {
        return toProductDomainModel(productRepositoryEnt.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())));
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findAll().forEach(productEnt -> productEnts.add(toProductDomainModel(productEnt)));

        return productEnts;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findByName(name).forEach(productEnt -> productEnts.add(toProductDomainModel(productEnt)));

        return productEnts;
    }

    @Override
    public List<Product> findByProducer(String producer) {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findByProducer(producer).forEach(productEnt -> productEnts.add(toProductDomainModel(productEnt)));

        return productEnts;
    }

    @Override
    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        if (producer.isPresent()) {
            return findByProducer(producer.get());
        } else if (name.isPresent()) {
            return findByName(name.get());
        } else {
            return findAllProducts();
        }
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return toProductDomainModel(productRepositoryEnt.update(id, toProductEnt(product)));
    }
}


