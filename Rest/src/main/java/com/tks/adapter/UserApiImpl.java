package com.tks.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.tks.User.BaseUser;
import com.tks.User.User;
import com.tks.api.UserRestApi;
import com.tks.dto.ChangePasswordDTO;
import com.tks.dto.RegisterDTO;
import com.tks.dto.user.BaseUserDTO;
import com.tks.dto.user.UserDTO;
import com.tks.security.JWTAuthTokenUtils;
import com.tks.userinterface.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.*;


@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@PermitAll
public class UserApiImpl implements UserRestApi {

    @Inject
    UserService userManager;

    @GET
    public Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) {
        return Response.status(Response.Status.OK).entity(userManager.getAllUsers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") UUID id) throws JOSEException {
        User user = userManager.getUserById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(id));
        jsonObject.addProperty("login", user.getLogin());
        String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
        return Response.status(Response.Status.OK).entity(UserDTO.userToDTO(user)).tag(ifMatch).build();
    }

    @GET
    @Path("/{id}/ongoingOrders")
    public Response getOngoingUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getOngoingUserOrders(userId)).build();
    }

    @GET
    @Path("/{id}/finishedOrders")
    public Response getFinishedUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getFinishedUserOrders(userId)).build();
    }

    @GET
    @Path("/{id}/allOrders")
    public Response getAllUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") UUID id, UserDTO updatedUser) throws ParseException, JOSEException {
        User user = userManager.updateUser(id, UserDTO.userDTOToDomainModel(updatedUser));
        return Response.ok().entity(UserDTO.userToDTO(user)).build();
    }

    @PUT
    @Path("/{id}/changePassword")
    public Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO) {
        userManager.changeUserPassword(id, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/cart")
    public Response getCart(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getUserCart(userId)).build();
    }

    @GET
    @Path("/{id}/orders")
    public Response getUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    @PUT
    @Path("/{id}/cart")
    public Response addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        return Response.ok().entity(userManager.addToCart(userId, productId, quantity)).build();
    }

    @DELETE
    @Path("/{id}/cart")
    public Response removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        return Response.ok().entity(userManager.removeFromCart(userId, productId)).build();
    }

    @POST
    @Path("/register")
    public Response register(@Valid UserDTO user) {
        user = new BaseUserDTO(user);
        return Response.ok(UserDTO.userToDTO(userManager.addUser(UserDTO.userDTOToDomainModel(user)))).build();
    }

    @POST
    public Response addUser(@Valid UserDTO user) {
        return Response.ok(userManager.addUser(UserDTO.userDTOToDomainModel(user))).build();
    }

    @PUT
    @Path("/{id}/suspendOrResume")
    public Response suspendOrResumeUser(@PathParam("id") UUID userId) {
        userManager.suspendOrResumeUser(userId);
        return Response.ok().build();
    }
}

