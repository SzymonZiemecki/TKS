package com.pas.endpoint;

import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

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

    private static final String ENTITY_NOT_FOUND_MESSAGE="Entity with given ID doesn't exist";

    @GET
    public List<Product> getAllProducts(@QueryParam("producer") Optional<String> producer, @QueryParam("name") Optional<String> name){
        if(producer.isPresent()){
            return getAllByProducer(producer.get());
        } else if (name.isPresent()) {
            return getAllByName(name.get());
        } else {
            return getAll();
        }
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") UUID id){
        return productManager.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE));
    }
    @POST
    public Product addProduct(@Valid Product product){
        return productManager.addItem(product);
    }

    @PATCH
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") UUID id, @Valid Product product){
        return productManager.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") UUID id){
        productManager.removeItem(id);
    }

    private List<Product> getAll(){
        return productManager.findAllProducts();
    }

    private List<Product> getAllByProducer(String producer){
        return productManager.findByProducer(producer);
    }

    private List<Product> getAllByName(String name){
        return productManager.findByName(name);
    }

}
