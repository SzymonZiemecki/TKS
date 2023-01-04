package com.pas.manager;

import com.pas.exception.BusinessLogicException;
import com.pas.model.Product.Product;
import com.pas.repository.OrderRepository;
import com.pas.repository.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;
import static com.pas.utils.ErrorInfo.PRODUCT_IN_UNFINISHED_ORDER;

@ApplicationScoped
public class ProductManager {

    @Inject
    private ProductRepository productRepository;

    @Inject
    OrderRepository orderRepository;

    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByProducer(String producer) {
        return productRepository.findByProducer(producer);
    }

    public Product addItem(Product product) {
        return productRepository.add(product);
    }

    public Product updateProduct(UUID id, Product product) {
        return productRepository.update(id, product);
    }

    public void removeItem(UUID id) {
        if (!isInOngoingOrder(id)) {
            productRepository.delete(id);
        } else {
            throw new BusinessLogicException(PRODUCT_IN_UNFINISHED_ORDER.getValue());
        }
    }

    private boolean isInOngoingOrder(UUID productId) {
        return orderRepository.filter(order -> !order.isDelivered()).stream()
                .map(order -> order.getItems().keySet())
                .flatMap(Collection::stream)
                .map(product -> product.getId().equals(productId))
                .filter($ -> $.equals(true))
                .findAny()
                .orElse(false);
    }

    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        if (producer.isPresent()) {
            return findByProducer(producer.get());
        } else if (name.isPresent()) {
            return findByName(name.get());
        } else {
            return findAllProducts();
        }
    }
}
