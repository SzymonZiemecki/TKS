package com.pas.endpoint;

import com.pas.manager.UserManager;
import com.pas.model.Cart;
import com.pas.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserApiImpl implements UserAPI {

    @Inject
    UserManager userManager;


    public List<User> getUsers(Optional<String> allMatchingByLogin, Optional<String> oneByLogin) {
        return userManager.findUsers(allMatchingByLogin, oneByLogin);
    }

    public User getUserById(UUID id) {
        return userManager.findById(id);
    }

    public List<Order> getOngoingUserOrders(UUID userId) {
        return userManager.findOngoingUserOrders(userId);
    }

    public List<Order> getFinishedUserOrders(UUID userId) {
        return userManager.findFinishedUserOrders(userId);
    }

    public User updateUser(UUID id, User updatedUser) {
        return userManager.updateUser(id, updatedUser);
    }

    public Cart getCart(UUID userId) {
        return userManager.findById(userId).getCart();
    }

    public List<Order> getUserOrders(UUID userId) {
        return userManager.findUserOrders(userId);
    }

    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        return userManager.addToCart(userId, productId, quantity);
    }

    public Cart removeFromCart(UUID userId, UUID productId) {
        return userManager.removeFromCart(userId, productId);
    }

    public User register(User user) {
        User toRegister = new BaseUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getAddress(), new Cart(), false, user.getAccountBalance());
        return userManager.register(toRegister);
    }

    public User addUser(User user) {
        return userManager.register(user);
    }

    public Response suspendOrResumeUser(UUID userId, boolean suspendOrResume) {
        userManager.suspendOrResumeUser(userId, suspendOrResume);
        return Response.ok().build();
    }
}
