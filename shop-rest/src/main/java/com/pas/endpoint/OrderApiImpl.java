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

@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
public class OrderApiImpl {

    @Inject
    OrderManager orderManager;

    @GET
    @RolesAllowed({"Manager", "Admin"})
    public List<Order> getAllOrders() {
        return orderManager.findAllOrders();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Order getOrderById(@PathParam("id") UUID id) {
        return orderManager.findById(id);
    }

    @GET
    @Path("/ongoing")
    @RolesAllowed({"Manager"})
    public List<Order> getOngoingOrders() {
        return orderManager.findOngoingOrders();
    }

    @GET
    @Path("/finished")
    @RolesAllowed({"Manager"})
    public List<Order> getFinishedOrders() {
        return orderManager.findFinishedOrders();
    }

    @POST
    @Path("/create")
    @RolesAllowed({"BaseUser"})
    public Order createOrder(@QueryParam("userId") UUID userId, @Valid Address shippingAddress) {
        return orderManager.createOrder(userId, shippingAddress);
    }

    @PUT
    @Path("/{id}/deliver")
    @RolesAllowed("Manager")
    public Response deliverOrder(@PathParam("id") UUID orderId) {
        orderManager.deliverOrder(orderId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response deleteOrder(@PathParam("id") UUID orderId) {
        orderManager.deleteOrder(orderId);
        return Response.ok().build();
    }


}
