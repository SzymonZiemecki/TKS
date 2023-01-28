package com.pas.endpoint;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.pas.endpoint.auth.JWTAuthTokenUtils;
import com.pas.manager.UserManager;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserApiImpl {

    @Inject
    UserManager userManager;

    @Inject
    Principal principal;

    @GET
    @RolesAllowed({"BaseUser", "Manager", "Admin", "Unauthorized"})
    public Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) throws JOSEException {
        List<User> users = userManager.findUsers(allMatchingByLogin, oneByLogin);
        if(oneByLogin.isPresent()){
            User user = users.get(0);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", String.valueOf(user.getId()));
            jsonObject.addProperty("login", user.getLogin());
            String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
            return Response.status(Response.Status.OK).entity(UserDTO.fromEntityToDTO(user)).tag(ifMatch).build();
        }
        return Response.status(Response.Status.OK).entity(UserDTO.entityListToDTO(userManager.findUsers(allMatchingByLogin, oneByLogin))).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    public Response getUserById(@PathParam("id") UUID id) throws JOSEException {
        User user = userManager.findById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(id));
        jsonObject.addProperty("login", user.getLogin());
        String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
        return Response.status(Response.Status.OK).entity(UserDTO.fromEntityToDTO(user)).tag(ifMatch).build();
    }

    @GET
    @Path("/{id}/ongoingOrders")
    @RolesAllowed({"BaseUser"})
    public List<Order> getOngoingUserOrders(@PathParam("id") UUID userId) {
        return userManager.findOngoingUserOrders(userId);
    }

    @GET
    @Path("/{id}/finishedOrders")
    @RolesAllowed({"BaseUser"})
    public List<Order> getFinishedUserOrders(@PathParam("id") UUID userId) {
        return userManager.findFinishedUserOrders(userId);
    }

    @GET
    @Path("/{id}/allOrders")
    @RolesAllowed({"BaseUser"})
    public List<Order> getAllUserOrders(@PathParam("id") UUID userId) {
        return userManager.findAllUserOrders(userId);
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response updateUser(@PathParam("id") UUID id, UserDTO updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", updatedUser.getId().toString());
        jsonObject.addProperty("login", updatedUser.getLogin());
        if(JWTAuthTokenUtils.verify(ifMatch, jsonObject.toString())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userManager.updateUser(id, updatedUser);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/changePassword")
    @RolesAllowed({"BaseUser", "Manager", "Admin"})
    public Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO) {
       userManager.changeUserPassword(id, changePasswordDTO);
       return Response.ok().build();
    }

    @GET
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Cart getCart(@PathParam("id") UUID userId) {
        return userManager.getUserCart(userId);
    }

    @GET
    @Path("/{id}/orders")
    @RolesAllowed({"BaseUser", "Admin"})
    public List<Order> getUserOrders(@PathParam("id") UUID userId) {
        return userManager.findUserOrders(userId);
    }

    @PUT
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Cart addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        return userManager.addToCart(userId, productId, quantity);
    }

    @DELETE
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Cart removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        return userManager.removeFromCart(userId, productId);
    }

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    public User register(@Valid RegisterDTO user) {
        return userManager.register(user);
    }

    @POST
    @RolesAllowed({"Admin"})
    public User addUser(@Valid RegisterDTO user) {
        return userManager.register(user);
    }

    @PUT
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    public Response suspendOrResumeUser(@PathParam("id") UUID userId) {
        userManager.suspendOrResumeUser(userId);
        return Response.ok().build();
    }
}
