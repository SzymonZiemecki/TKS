/*
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

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
@Remote
public interface ProductRestApi {
    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    Response getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name);
    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    Response getProductById(@PathParam("id") UUID id);
    @POST
    @RolesAllowed({"Manager"})
    Response addProduct(@Valid Product product);
    @PUT
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Response updateProduct(@PathParam("id") UUID id, @Valid Product product);
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Response deleteProduct(@PathParam("id") UUID id);
}
*/
