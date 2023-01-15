package com.pas.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductApiImpl implements ProductAPI {

    @Inject
    ProductManager productManager;

    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
        return productManager.getProducts(producer, name);
    }

    public Product getProductById(UUID id) {
        return productManager.findById(id);
    }

    public Product addProduct(@Valid Product product) {
        return productManager.addItem(product);
    }

    public Product updateProduct(UUID id, Product product) {
        return productManager.updateProduct(id, product);
    }

    public Response deleteProduct(UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }

}
