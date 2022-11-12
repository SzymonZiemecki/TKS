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
import com.pas.model.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

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
    public List<User> getAllUsers() {
        return userManager.findAllUsers();
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") UUID id) {
        return userManager.findById(id);
    }

 /*   @GET
    public User getUserByLogin(@QueryParam("login") String login) {
        return userManager.findByLogin(login);
    }*/

    @GET
    @Path("/{id}/ongoingOrders")
    public List<Order> getOngoingUserOrders(@PathParam("id") UUID userId){
        return userManager.findOngoingUserOrders(userId);
    }
    @GET
    @Path("/{id}/finishedOrders")
    public List<Order> getFinishedUserOrders(@PathParam("id") UUID userId){
        return userManager.findFinishedUserOrders(userId);
    }

    @PUT
    @Path("/{id}")
    public void updateUser(@PathParam("id") UUID id, User updatedUser) {
        userManager.updateUser(id, updatedUser);
    }

    @PUT
    @Path("/{id}/cart")
    public void addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        userManager.addToCart(userId, productId, quantity);
    }

    @PATCH
    @Path("/{id}/cart")
    public void removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId) {
        userManager.removeFromCart(userId, productId);
    }

    @POST
    @Path("/register")
    public User register(UserDTO user) {
        User toRegister = new BaseUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), true, new Address("test", "test", "test", "test", "test"), new Cart(), false, 120d);
        Product product = new Tv(10, 20D, "test", "test", "test", "test", "test", "test", "test");
        productManager.addItem(product);
        return userManager.register(toRegister);
    }

    @PATCH
    @Path("/{id}/suspendOrResume")
    public void suspendOrResumeUser(@PathParam("id") UUID userId, @QueryParam("suspendOrResume") boolean suspendOrResume) {
        userManager.suspendOrResumeUser(userId, suspendOrResume);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") UUID id){
        userManager.deleteUser(id);
    }
}
