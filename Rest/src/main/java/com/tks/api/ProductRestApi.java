package com.tks.api;

import com.tks.Product.Product;
import com.tks.dto.product.ProductDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.UUID;

public interface ProductRestApi {
    Response getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name);
    Response getProductById(@PathParam("id") UUID id);
    Response addProduct(@Valid ProductDTO product);
    Response updateProduct(@PathParam("id") UUID id, @Valid ProductDTO product);
    Response deleteProduct(@PathParam("id") UUID id);
}
