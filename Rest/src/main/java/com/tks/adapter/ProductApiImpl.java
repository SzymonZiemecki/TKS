/*
package com.tks.adapter;

import com.tks.Product.Product;
import com.tks.api.ProductRestApi;
import com.tks.dto.product.ProductDTO;
import com.tks.userinterface.ProductService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.UUID;

public class ProductApiImpl implements ProductRestApi {
    @Inject
    ProductService productManager;

    @Override
    public Response getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name) {
        return Response.ok().entity(productManager.getProducts(producer, name)).build();
    }

    @Override
    public Response getProductById(@PathParam("id") UUID id) {
        return Response.ok().entity(productManager.findById(id)).build();
    }

    @Override
    public Response addProduct(@Valid Product product) {
        return Response.ok().entity(productManager.addItem(product)).build();
    }

    @Override
    public Response updateProduct(@PathParam("id") UUID id, @Valid Product product) {
        return Response.ok().entity(productManager.updateProduct(id, product)).build();
    }

    @Override
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }

}
*/
