package com.pas.endpoint;

import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class ProductAPI {

    @Inject
    ProductManager productManager;

    @GET
    public List<Product> getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name) {
        return productManager.getProducts(producer, name);
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") UUID id) {
        return productManager.findById(id);
    }

    @POST
    public Product addProduct(@Valid Product product) {
        return productManager.addItem(product);
    }

    @PATCH
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") UUID id, @Valid Product product) {
        return productManager.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }

}
