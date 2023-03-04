package com.tks.aggregates;

import com.tks.mapper.ModelMapperBean;
import data.model.OrderEnt;
import data.user.CartItemEnt;
import jakarta.enterprise.context.ApplicationScoped;
import repository.OrderEntRepository;
import repository.ProductEntRepository;

import com.tks.Product.Product;

import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.products.AddProductPort;
import com.tks.infrastructure.products.DeleteProductPort;
import com.tks.infrastructure.products.GetProductPort;
import com.tks.infrastructure.products.UpdateProductPort;



import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;

import java.util.*;

import static data.utils.ErrorInfoEnt.ENTITY_NOT_FOUND_MESSAGE;
import static data.utils.ErrorInfoEnt.PRODUCT_IN_UNFINISHED_ORDER;

@ApplicationScoped
public class ProductAdapter implements AddProductPort, DeleteProductPort, GetProductPort, UpdateProductPort {

    @Inject
    private ProductEntRepository productRepositoryEnt;

    @Inject
    private OrderEntRepository orderRepository;

    @Inject
    private ModelMapperBean mapper;

    @Override
    @SneakyThrows
    public Product addItem(Product product) {
        return ModelMapperBean.toDomainModel(productRepositoryEnt.add(ModelMapperBean.toEntModel(product)));
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
        return orderRepository.filter(order -> !order.isDelivered()).stream()
                .map(OrderEnt::getItems)
                .flatMap(Collection::stream)
                .map(CartItemEnt::getProduct)
                .map(product -> product.equals(productId))
                .filter($ -> $.equals(true))
                .findAny()
                .orElse(false);
    }


    @Override
    public Product findById(UUID id) {
        return mapper.toDomainModel(productRepositoryEnt.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())));
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findAll().forEach(productEnt -> productEnts.add(ModelMapperBean.toDomainModel(productEnt)));

        return productEnts;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findByName(name).forEach(productEnt -> productEnts.add(ModelMapperBean.toDomainModel(productEnt)));
        return productEnts;
    }

    @Override
    public List<Product> findByProducer(String producer) {
        List<Product> productEnts = new ArrayList<>();
        productRepositoryEnt.findByProducer(producer).forEach(productEnt -> productEnts.add(ModelMapperBean.toDomainModel(productEnt)));
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
        return mapper.toDomainModel(productRepositoryEnt.update(id, ModelMapperBean.toEntModel(product)));
    }
}


