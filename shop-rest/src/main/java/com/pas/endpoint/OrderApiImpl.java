package com.pas.endpoint;

import com.pas.model.Address;
import com.pas.model.Order;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.manager.OrderManager;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderApiImpl implements OrderAPI {

    @Inject
    OrderManager orderManager;

    public List<Order> getAllOrders() {
        return orderManager.findAllOrders();
    }

    public Order getOrderById(UUID id) {
        return orderManager.findById(id);
    }

    public List<Order> getOngoingOrders() {
        return orderManager.findOngoingOrders();
    }

    public List<Order> getFinishedOrders() {
        return orderManager.findFinishedOrders();
    }

    public Order createOrder(UUID userId, Address shippingAddress) {
        return orderManager.createOrder(userId, shippingAddress);
    }
    public Response deliverOrder(UUID orderId) {
        orderManager.deliverOrder(orderId);
        return Response.ok().build();
    }

    public Response deleteOrder(UUID orderId) {
        orderManager.deleteOrder(orderId);
        return Response.ok().build();
    }


}
