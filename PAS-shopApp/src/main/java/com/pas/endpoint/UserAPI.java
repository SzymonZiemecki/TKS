package com.pas.endpoint;

import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Laptop;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import com.pas.model.dto.OrderDTO;
import com.pas.model.dto.UserDTO;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class UserAPI {

    @Inject
    UserManager userManager;

    @Inject
    ProductManager productManager;

    @GET
    public List<UserDTO> getAllUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin) {
        if (allMatchingByLogin.isPresent()) {
            return getUsersByLogin(allMatchingByLogin.get());
        } else {
            return oneByLogin.map(s -> Collections.singletonList(UserDTO.fromEntityToDTO(userManager.findOneByLogin(s)))).orElseGet(this::getAllUsers);
        }
    }

    @GET
    @Path("/{id}")
    public UserDTO getUserById(@PathParam("id") UUID id) {
        return UserDTO.fromEntityToDTO(userManager.findById(id));
    }

    @GET
    @Path("/{id}/ongoingOrders")
    public List<OrderDTO> getOngoingUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findOngoingUserOrders(userId));
    }

    @GET
    @Path("/{id}/finishedOrders")
    public List<OrderDTO> getFinishedUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findFinishedUserOrders(userId));
    }

    @PUT
    @Path("/{id}")
    public UserDTO updateUser(@PathParam("id") UUID id, @Valid User updatedUser) {
        return UserDTO.fromEntityToDTO(userManager.updateUser(id, updatedUser));
    }

    @GET
    @Path("/{id}/cart")
    public Cart getCart(@PathParam("id") UUID userId) {
        return userManager.findById(userId).getCart();
    }

    @GET
    @Path("/{id}/orders")
    public List<OrderDTO> getUserOrders(@PathParam("id") UUID userId) {
        return OrderDTO.entityListToDTO(userManager.findUserOrders(userId));
    }

    @PUT
    @Path("/{id}/cart")
    public Cart addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        return userManager.addToCart(userId, productId, quantity);
    }

    @PATCH
    @Path("/{id}/cart")
    public Cart removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        return userManager.removeFromCart(userId, productId);
    }

    @POST
    @Path("/register")
    public UserDTO register(@Valid UserDTO user) {
        User toRegister = new BaseUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getAddress(), new Cart(), false, user.getAccountBalance());
        return UserDTO.fromEntityToDTO(userManager.register(toRegister));
    }
    @POST
    public User addUser(@Valid User user){
        return userManager.register(user);
    }
    @PATCH
    @Path("/{id}/suspendOrResume")
    public void suspendOrResumeUser(@PathParam("id") UUID userId, @QueryParam("suspendOrResume") boolean suspendOrResume) {
        userManager.suspendOrResumeUser(userId, suspendOrResume);
    }

    private List<UserDTO> getAllUsers() {
        return userManager.findAllUsers().stream()
                .map(UserDTO::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    private List<UserDTO> getUsersByLogin(String login) {
        return UserDTO.entityListToDTO(userManager.findByLogin(login));

    }
}
