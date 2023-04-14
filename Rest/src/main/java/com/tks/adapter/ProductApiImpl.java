package com.tks.adapter;

import com.tks.Product.Product;
import com.tks.api.ProductRestApi;
import com.tks.dto.product.ProductDTO;
import com.tks.userinterface.ProductService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Path("/products")
@Consumes("application/json")
@Produces("application/json")
@PermitAll
public class ProductApiImpl implements ProductRestApi {
    @Inject
    ProductService productManager;

    @GET
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
    public Response getProductById (@PathParam("id") UUID id) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.findById(id))).build();
    }

    @POST
    public Response addProduct(@Valid ProductDTO product) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.addItem(ProductDTO.productDTOToDomainModel(product)))).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") UUID id, @Valid ProductDTO product) {
        return Response.ok().entity(ProductDTO.productToDTO(productManager.updateProduct(id, ProductDTO.productDTOToDomainModel(product)))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }
}
