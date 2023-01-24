package com.pas.endpoint;

import com.pas.manager.UserManager;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.dto.ChangePasswordDTO;
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
    public List<User> getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) {
        return userManager.findUsers(allMatchingByLogin, oneByLogin);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    public User getUserById(@PathParam("id") UUID id) {
        return userManager.findById(id);
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
    @RolesAllowed({"Admin"})
    public User updateUser(@PathParam("id") UUID id, User updatedUser) {
        return userManager.updateUser(id, updatedUser);
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

    @PATCH
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Cart removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        return userManager.removeFromCart(userId, productId);
    }

    @POST
    @Path("/register")
    @RolesAllowed({"Unauthorized"})
    public User register(@Valid User user) {
        User toRegister = new BaseUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getAddress(), new Cart(), false, user.getAccountBalance());
        return userManager.register(toRegister);
    }

    @POST
    @RolesAllowed({"Admin"})
    public User addUser(@Valid User user) {
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
