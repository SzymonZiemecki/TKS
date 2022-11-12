package com.pas.endpoint;

import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class ProductAPI {

    @Inject
    ProductManager productManager;

    @GET
    public List<Product> getAllProducts(){
        return productManager.findAllProducts();
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") UUID id){
        return productManager.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with given id doesnt exist"));
    }

/*    @GET
    public List<Product> getProductByProducer(@QueryParam("producer") String producer){
        return productManager.findByProducer(producer);
    }

    @GET
    public List<Product> getProductByName(@QueryParam("name") String name){
        return productManager.findByName(name);
    }*/

    @POST
    public Product addProduct(Product product){
        return productManager.addItem(product);
    }

    @PATCH
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") UUID id, Product product){
        return productManager.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") UUID id){
        productManager.removeItem(id);
    }

}
