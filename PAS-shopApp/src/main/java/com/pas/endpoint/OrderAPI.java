package com.pas.endpoint;

import com.pas.manager.OrderManager;
import com.pas.model.Address;
import com.pas.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class OrderAPI {

    @Inject
    OrderManager orderManager;


    @GET
    public List<Order> getAllOrders(){
        return orderManager.findAllOrders();
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") UUID id){
        return orderManager.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with given id doesnt exist"));
    }

    @GET
    @Path("/ongoing")
    public List<Order> getOngoingOrders(){
        return orderManager.findOngoingOrders();
    }

    @GET
    @Path("/finished")
    public List<Order> getFinishedOrders(){
        return orderManager.findFinishedOrders();
    }

    @POST
    @Path("/create")
    public Order createOrder(@QueryParam("userId") UUID userId, @Valid Address shippingAddress){
        return orderManager.createOrder(userId, shippingAddress);
    }

    @PATCH
    @Path("/{id}")
    public void deliverOrder(@PathParam("id") UUID orderId){
        orderManager.deliverOrder(orderId);
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrder(@PathParam("id") UUID orderId){
        orderManager.deleteOrder(orderId);
    }


}
