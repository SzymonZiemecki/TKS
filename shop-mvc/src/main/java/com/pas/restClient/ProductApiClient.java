package com.pas.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductApiClient extends RestClient<Product>{

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public ProductApiClient() {
        super(Product.class, new GenericType<Product>(){}, "products", new TypeReference<List<Product>>(){}, new TypeReference<Product>(){});
    }

    public List<Product> getAllProducts(){
       return this.getAllRequest();
    }

    public Product getProductById(UUID id) {
        return this.getByIdRequest(id);
    }

    public void addProduct(Product product){
        this.addRequest(product);
    }

    public void updateProduct(UUID id, Product product){
        this.updateRequest(id, product);
    }

    public void deleteProductById(UUID id){
        this.deleteRequest(id);
    }

    public List<Order> ordersContainingProduct(UUID id){
        return (List<Order>) this.customGet("/" + id.toString() + "/ordersContainingProduct", new TypeReference<List<Order>>(){});
    }
}
