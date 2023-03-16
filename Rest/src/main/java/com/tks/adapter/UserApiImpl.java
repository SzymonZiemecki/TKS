package com.tks.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.tks.User.User;
import com.tks.dto.ChangePasswordDTO;
import com.tks.dto.RegisterDTO;
import com.tks.security.JWTAuthTokenUtils;
import com.tks.userinterface.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

import static com.tks.converter.Mapper.toDTOModel;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@Remote
public class UserApiImpl {

    @Inject
    UserService userManager;

    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) {
        return Response.status(Response.Status.OK).entity(userManager.getAllUsersByLogin(String.valueOf(allMatchingByLogin))).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    public Response getUserById(@PathParam("id") UUID id) throws JOSEException {
        User user = userManager.getUserById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(id));
        jsonObject.addProperty("login", user.getLogin());
        String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
        return Response.status(Response.Status.OK).entity(user).tag(ifMatch).build();
    }

    @GET
    @Path("/{id}/ongoingOrders")
    @RolesAllowed({"BaseUser"})
    public Response getOngoingUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getOngoingUserOrders(userId)).build();
    }

    @GET
    @Path("/{id}/finishedOrders")
    @RolesAllowed({"BaseUser"})
    public Response getFinishedUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getFinishedUserOrders(userId)).build();
    }

    @GET
    @Path("/{id}/allOrders")
    @RolesAllowed({"BaseUser"})
    public Response getAllUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response updateUser(@PathParam("id") UUID id, User updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException {
        return null;
    }

//    @Override
//    public Response updateUser(UUID id, UserDTO updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("id", updatedUser.getId().toString());
//        jsonObject.addProperty("login", updatedUser.getLogin());
//        if(!JWTAuthTokenUtils.verify(ifMatch, jsonObject.toString())){
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//        userManager.updateUser(id, updatedUser);
//        return Response.ok().build();
//    }

    @PUT
    @Path("/{id}/changePassword")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO) {
       userManager.changeUserPassword(id, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
       return Response.ok().build();
    }

    @GET
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Response getCart(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getUserCart(userId)).build();
    }

    @GET
    @Path("/{id}/orders")
    @RolesAllowed({"BaseUser", "Admin"})
    public Response getUserOrders(@PathParam("id") UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    @PUT
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Response addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        return Response.ok().entity(userManager.addToCart(userId, productId, quantity)).build();
    }

    @DELETE
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Response removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        return Response.ok().entity(userManager.removeFromCart(userId, productId)).build();
    }

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    public Response register(@Valid RegisterDTO user) {
        return Response.ok(userManager.addUser(toDTOModel(user))).build();
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response addUser(@Valid RegisterDTO user) {
        return Response.ok(userManager.addUser(toDTOModel(user))).build();
    }

    @PUT
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    public Response suspendOrResumeUser(@PathParam("id") UUID userId) {
        userManager.suspendOrResumeUser(userId);
        return Response.ok().build();
    }
}

