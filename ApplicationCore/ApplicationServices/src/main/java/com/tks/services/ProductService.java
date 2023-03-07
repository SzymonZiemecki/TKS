package com.tks.services;

import com.tks.Product.Product;
import com.tks.infrastructure.carts.AddToUserCartPort;
import com.tks.infrastructure.products.AddProductPort;
import com.tks.infrastructure.products.DeleteProductPort;
import com.tks.infrastructure.products.GetProductPort;
import com.tks.infrastructure.products.UpdateProductPort;
import com.tks.userinterface.ProductUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductService implements ProductUseCase {

    @Inject
    AddProductPort addProductPort;
    @Inject
    UpdateProductPort updateProductPort;
    @Inject
    GetProductPort getProductPort;
    @Inject
    DeleteProductPort deleteProductPort;

    @Override
    public Product findById(UUID id) {
        return getProductPort.findById(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return getProductPort.findAllProducts();
    }

    @Override
    public List<Product> findByName(String name) {
        return getProductPort.findByName(name);
    }

    @Override
    public List<Product> findByProducer(String producer) {
        return getProductPort.findByProducer(producer);
    }

    @Override
    public Product addItem(Product product) {
        return addProductPort.addItem(product);
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return updateProductPort.updateProduct(id, product);
    }

    @Override
    public void removeItem(UUID id) {
        deleteProductPort.removeItem(id);
    }

    @Override
    public boolean isInOngoingOrder(UUID productId) {
        return getProductPort.isInOngoingOrder(productId);
    }

    @Override
    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        return getProductPort.getProducts(producer, name);
    }
}
