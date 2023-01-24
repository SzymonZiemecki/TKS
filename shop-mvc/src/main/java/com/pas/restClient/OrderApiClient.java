package com.pas.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Address;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class OrderApiClient extends RestClient<Order> implements Serializable {

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public OrderApiClient() {
        super();
    }

    public void createOrder(UUID id, Address address) {
        WebTarget webTarget = client.path("/orders").queryParam("userId", id.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(address));
    }

    public void deliverOrder(UUID id) {
        WebTarget webTarget = client.path("/orders").queryParam("userId", id.toString());
        Response response = webTarget.request()
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .method("PATCH");
    }
    public List<Order> getAllOrders(){
        WebTarget webTarget = client.path("/orders");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<Order>>(){});
    }
}
