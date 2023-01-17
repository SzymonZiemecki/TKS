package com.pas.endpoint;

import com.pas.model.Order;
import jakarta.annotation.security.RolesAllowed;
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

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductApiImpl{

    @Inject
    ProductManager productManager;
    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public List<Product> getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name) {
        return productManager.getProducts(producer, name);
    }
    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public Product getProductById(@PathParam("id") UUID id) {
        return productManager.findById(id);
    }
    @POST
    @RolesAllowed({"Manager"})
    public Product addProduct(@Valid Product product) {
        return productManager.addItem(product);
    }
    @PUT
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Product updateProduct(@PathParam("id") UUID id, @Valid Product product) {
        return productManager.updateProduct(id, product);
    }
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }
    @GET
    @Path("/{id}/ordersContainingProduct")
    public List<Order> ordersContainingProduct(@PathParam("id") UUID id) {
        return productManager.ordersContainingProduct(productManager.findById(id));
    }

}
