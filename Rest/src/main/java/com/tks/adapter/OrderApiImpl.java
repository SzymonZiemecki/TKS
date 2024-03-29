package com.tks.adapter;

 import com.tks.dto.AddressDTO;
import com.tks.dto.OrderDTO;
import com.tks.model.Address;
import com.tks.model.Order;
import com.tks.userinterface.OrderService;
 import jakarta.annotation.security.PermitAll;
 import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
@PermitAll
public class OrderApiImpl  {

    @Inject
    OrderService orderManager;

    @GET
    public Response getAllOrders() {
        return Response.ok().entity(orderManager.getAllOrders()).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") UUID id) {
        OrderDTO order = new OrderDTO();
        try {
            orderManager.getOrderById(id);
        } catch (EntityNotFoundException e) {
            return Response.status(404).build();
        }
        return Response.ok().entity(order).build();
    }

    @GET
    @Path("/ongoing")
    public Response getOngoingOrders() {
        return Response.ok().entity(orderManager.getOngoingOrders()).build();
    }

    @GET
    @Path("/finished")
    public Response getFinishedOrders() {
        return Response.ok().entity(orderManager.getFinishedOrders()).build();
    }

    @POST
    @Path("/create")
    public Response createOrder(@QueryParam("userId") UUID userId, @Valid AddressDTO shippingAddress) {
        Order order = new Order();
        try {
            order = orderManager.createOrder(userId, AddressDTO.addressDTOToDomainModel(shippingAddress));
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok(OrderDTO.orderToDTO(order)).build();
    }

    @PUT
    @Path("/{id}/deliver")
    public Response deliverOrder(@PathParam("id") UUID orderId) {
        try {
            orderManager.deliverOrder(orderId);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") UUID orderId) {
        try {
            orderManager.deleteOrder(orderId);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


}
