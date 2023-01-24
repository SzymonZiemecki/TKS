package com.pas.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class ProductApiClient extends RestClient<Product> implements Serializable {

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public ProductApiClient() {
        super(Product.class, new GenericType<Product>(){}, "products", new TypeReference<List<Product>>(){}, new TypeReference<Product>(){});
    }

    public List<Product> getAllProducts(){
        WebTarget webTarget = client.path("/products");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<Product>>(){});
    }

    public Product getProductById(UUID id) {
        WebTarget webTarget = client.path("/products/" + id.toString());
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(Product.class);
    }

    public void addProduct(Product product){
        WebTarget webTarget = client.path("/products");
        webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(product));
    }

    public void updateProduct(UUID id, Product product){
        WebTarget webTarget = client.path("/products/" + id.toString());
        webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(product));
    }

    public void deleteProductById(UUID id){
        WebTarget webTarget = client.path("/products/" + id.toString());
        webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .delete();
    }

    public List<Order> ordersContainingProduct(UUID id){
        WebTarget webTarget = client.path("/products/" + id.toString() + "/ordersContainingProduct");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<Order>>(){});
    }
}
