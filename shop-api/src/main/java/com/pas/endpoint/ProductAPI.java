package com.pas.endpoint;

import com.pas.model.Product.Product;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public interface ProductAPI {

    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    List<Product> getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name);

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    Product getProductById(@PathParam("id") UUID id);

    @POST
    @RolesAllowed({"Manager"})
    Product addProduct(@Valid Product product);

    @PATCH
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Product updateProduct(@PathParam("id") UUID id, @Valid Product product);

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Response deleteProduct(@PathParam("id") UUID id);

}
