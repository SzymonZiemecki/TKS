package com.pas.endpoint;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.User.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public interface UserAPI {

    @GET
    @RolesAllowed({"Admin"})
    List<User> getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin);

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    User getUserById(@PathParam("id") UUID id);

    @GET
    @Path("/{id}/ongoingOrders")
    @RolesAllowed({"BaseUser"})
    List<Order> getOngoingUserOrders(@PathParam("id") UUID userId);

    @GET
    @Path("/{id}/finishedOrders")
    @RolesAllowed({"BaseUser"})
    List<Order> getFinishedUserOrders(@PathParam("id") UUID userId);

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    User updateUser(@PathParam("id") UUID id, User updatedUser);

    @GET
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Cart getCart(@PathParam("id") UUID userId);

    @GET
    @Path("/{id}/orders")
    @RolesAllowed({"BaseUser", "Admin"})
    List<Order> getUserOrders(@PathParam("id") UUID userId);

    @PUT
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Cart addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity);

    @PATCH
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Cart removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId);

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    User register(@Valid User user);

    @POST
    @RolesAllowed({"Admin"})
    User addUser(@Valid User user);

    @PATCH
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    Response suspendOrResumeUser(@PathParam("id") UUID userId, @QueryParam("suspendOrResume") boolean suspendOrResume);
}
