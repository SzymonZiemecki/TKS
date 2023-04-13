package com.tks.soap.endpoints;

import com.tks.Product.Product;
import jakarta.jws.WebService;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.UUID;

@WebService
public interface ProductEndpointApi {
    Response getProducts(Optional<String> producer, Optional<String> name);

    Response getProductById(UUID id);

    Response addProduct(Product product);

    Response updateProduct(UUID id, Product product);

    Response deleteProduct(UUID id);
}
