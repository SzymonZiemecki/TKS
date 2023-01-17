package com.pas.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Address;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.GenericType;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderApiClient extends RestClient<Order>{

    public OrderApiClient() {
        super(Order.class, new GenericType<Order>(){}, "orders",new TypeReference<List<Order>>(){}, new TypeReference<Order>(){});
    }

    public void createOrder(UUID id, Address address) {
        customPost("/create?userId="+id.toString(), address, new TypeReference<Address>(){});
    }

    public void deliverOrder(UUID id) {
        customPatch("/" + id.toString() + "/deliver");
    }
}
