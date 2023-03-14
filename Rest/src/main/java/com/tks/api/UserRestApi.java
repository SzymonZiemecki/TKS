package com.tks.api;

import com.nimbusds.jose.JOSEException;
import com.tks.User.User;
import com.tks.dto.*;
import com.tks.model.Cart;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@Remote
public interface UserRestApi {

    @GET
    @RolesAllowed({"BaseUser", "Manager", "Admin", "Unauthorized"})
    Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin);

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    Response getUserById(@PathParam("id") UUID id) throws JOSEException;

    @GET
    @Path("/{id}/ongoingOrders")
    @RolesAllowed({"BaseUser"})
    Response getOngoingUserOrders(@PathParam("id") UUID userId);

    @GET
    @Path("/{id}/finishedOrders")
    @RolesAllowed({"BaseUser"})
    Response getFinishedUserOrders(@PathParam("id") UUID userId);

    @GET
    @Path("/{id}/allOrders")
    @RolesAllowed({"BaseUser"})
    Response getAllUserOrders(@PathParam("id") UUID userId);

    @PUT
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    Response updateUser(@PathParam("id") UUID id, User updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException;

    @PUT
    @Path("/{id}/changePassword")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO);

    @GET
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Response getCart(@PathParam("id") UUID userId);

    @GET
    @Path("/{id}/orders")
    @RolesAllowed({"BaseUser", "Admin"})
    Response getUserOrders(@PathParam("id") UUID userId);

    @PUT
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Response addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity);

    @DELETE
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    Response removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId);

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    Response register(@Valid RegisterDTO user);

    @POST
    @RolesAllowed({"Admin"})
    Response addUser(@Valid RegisterDTO user);

    @PUT
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    Response suspendOrResumeUser(@PathParam("id") UUID userId);
}
