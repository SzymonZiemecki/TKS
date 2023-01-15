package com.pas.endpoint;

import com.pas.model.Address;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.model.Order;

import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
public interface OrderAPI {
    @GET
    @RolesAllowed({"Manager"})
    List<Order> getAllOrders();

    @GET
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Order getOrderById(@PathParam("id") UUID id);

    @GET
    @Path("/ongoing")
    @RolesAllowed({"Manager"})
    List<Order> getOngoingOrders();

    @GET
    @Path("/finished")
    @RolesAllowed({"Manager"})
    List<Order> getFinishedOrders();

    @POST
    @Path("/create")
    @RolesAllowed({"BaseUser"})
    Order createOrder(@QueryParam("userId") UUID userId, @Valid Address shippingAddress);

    @PATCH
    @Path("/{id}/deliver")
    @RolesAllowed("Manager")
    Response deliverOrder(@PathParam("id") UUID orderId);

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    Response deleteOrder(@PathParam("id") UUID orderId);


}
