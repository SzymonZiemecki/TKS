package com.tks.adapter;

import com.tks.Product.Product;
import com.tks.data.product.ProductEnt;
import com.tks.dto.product.ProductDTO;
import com.tks.userinterface.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tks.converter.DTOModelMapper.*;

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
@Remote
public class ProductApiImpl {
    @Inject
    ProductService productManager;

    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public Response getProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name) {

        List<Product> productList= productManager.getProducts(producer, name);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product: productList) {
            productDTOList.add(ProductDTO.productToDTO(product));
        }

        return Response.ok().entity(productDTOList).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public Response getProductById (@PathParam("id") UUID id) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.findById(id))).build();
    }

    @POST
    @RolesAllowed({"Manager"})
    public Response addProduct(@Valid Product product) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.addItem(product))).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response updateProduct(@PathParam("id") UUID id, @Valid Product product) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.updateProduct(id, product))).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }
}
