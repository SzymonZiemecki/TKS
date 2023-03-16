/*
package com.tks.adapter;

import com.tks.api.OrderRestApi;
import com.tks.dto.AddressDTO;
import com.tks.dto.OrderDTO;
import com.tks.model.Address;
import com.tks.model.Order;
import com.tks.userinterface.OrderService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

public class OrderApiImpl implements OrderRestApi {

    @Inject
    OrderService orderManager;

    @Override
    public Response getAllOrders() {
        return Response.ok().entity(orderManager.getAllOrders()).build();
    }

    @Override
    public Response getOrderById(UUID id) {
        OrderDTO order = new OrderDTO();
        try {
            orderManager.getOrderById(id);
        } catch (EntityNotFoundException e) {
            return Response.status(404).build();
        }
        return Response.ok().entity(order).build();
    }

    @Override
    public Response getOngoingOrders() {
        return Response.ok().entity(orderManager.getOngoingOrders()).build();
    }

    @Override
    public Response getFinishedOrders() {
        return Response.ok().entity(orderManager.getFinishedOrders()).build();
    }

    @Override
    public Response createOrder(UUID userId, AddressDTO shippingAddress) {
        Order order = new Order();
        try {
            order = orderManager.createOrder(userId, new Address());
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok(order).build();
    }

    @Override
    public Response deliverOrder(UUID orderId) {
        try {
            orderManager.deliverOrder(orderId);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @Override
    public Response deleteOrder(UUID orderId) {
        try {
            orderManager.deleteOrder(orderId);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


}
*/
