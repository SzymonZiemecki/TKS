//package com.tks.api;
//
//import com.tks.dto.AddressDTO;
//import com.tks.dto.OrderDTO;
//import jakarta.annotation.security.RolesAllowed;
//import jakarta.ejb.Remote;
//import jakarta.validation.Valid;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//import java.util.UUID;
//
//@Path("/orders")
//@Consumes("application/json")
//@Produces("application/json")
//@Remote
//public interface OrderRestApi {
//    @GET
//    @RolesAllowed({"Manager", "Admin"})
//    Response getAllOrders();
//
//    @GET
//    @Path("/{id}")
//    @RolesAllowed({"Manager"})
//    Response getOrderById(@PathParam("id") UUID id);
//
//    @GET
//    @Path("/ongoing")
//    @RolesAllowed({"Manager"})
//    Response getOngoingOrders();
//
//    @GET
//    @Path("/finished")
//    @RolesAllowed({"Manager"})
//    Response getFinishedOrders();
//
//    @POST
//    @Path("/create")
//    @RolesAllowed({"BaseUser"})
//    Response createOrder(@QueryParam("userId") UUID userId, @Valid AddressDTO shippingAddress);
//
//    @PUT
//    @Path("/{id}/deliver")
//    @RolesAllowed("Manager")
//    Response deliverOrder(@PathParam("id") UUID orderId);
//
//    @DELETE
//    @Path("/{id}")
//    @RolesAllowed({"Manager"})
//    Response deleteOrder(@PathParam("id") UUID orderId);
//}
