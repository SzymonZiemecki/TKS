package com.pas.endpoint;

import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.Cart;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import com.pas.model.dto.OrderDTO;
import com.pas.model.dto.UserDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class UserAPI {

    @Inject
    UserManager userManager;


    @GET
    @RolesAllowed({"Admin"})
    public List<UserDTO> getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) {
        return UserDTO.entityListToDTO(userManager.findUsers(allMatchingByLogin, oneByLogin));
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"BaseUser", "Admin"})
    public UserDTO getUserById(@PathParam("id") UUID id) {
        return UserDTO.fromEntityToDTO(userManager.findById(id));
    }

    @GET
    @Path("/{id}/ongoingOrders")
    @RolesAllowed({"BaseUser"})
    public List<OrderDTO> getOngoingUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findOngoingUserOrders(userId));
    }

    @GET
    @Path("/{id}/finishedOrders")
    @RolesAllowed({"BaseUser"})
    public List<OrderDTO> getFinishedUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findFinishedUserOrders(userId));
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public UserDTO updateUser(@PathParam("id") UUID id, User updatedUser) {
        return UserDTO.fromEntityToDTO(userManager.updateUser(id, updatedUser));
    }

    @GET
    @Path("/{id}/cart")
    @RolesAllowed({"BaseUser"})
    public Cart getCart(@PathParam("id") UUID userId) {
        return userManager.findById(userId).getCart();
    }

    @GET
    @Path("/{id}/orders")
    @RolesAllowed({"BaseUser", "Admin"})
    public List<OrderDTO> getUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findUserOrders(userId));
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
    public UserDTO register(@Valid UserDTO user) {
        User toRegister = new BaseUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getAddress(), new Cart(), false, user.getAccountBalance());
        return UserDTO.fromEntityToDTO(userManager.register(toRegister));
    }

    @POST
    @RolesAllowed({"Admin"})
    public User addUser(@Valid User user) {
        return userManager.register(user);
    }

    @PATCH
    @Path("/{id}/suspendOrResume")
    @RolesAllowed({"Admin"})
    public Response suspendOrResumeUser(@PathParam("id") UUID userId, @QueryParam("suspendOrResume") boolean suspendOrResume) {
        userManager.suspendOrResumeUser(userId, suspendOrResume);
        return Response.ok().build();
    }
}
