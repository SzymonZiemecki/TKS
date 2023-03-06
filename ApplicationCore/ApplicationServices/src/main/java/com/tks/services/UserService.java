package com.tks.services;


import com.tks.User.User;
import com.tks.infrastructure.carts.AddToUserCartPort;
import com.tks.infrastructure.carts.RemoveFromUserCartPort;
import com.tks.infrastructure.users.AddUserPort;
import com.tks.infrastructure.users.GetUserPort;
import com.tks.infrastructure.users.UpdateUserPort;
import com.tks.model.Cart;
import com.tks.model.Order;
import com.tks.userinterface.UserUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService implements UserUseCase {
    @Inject
    private AddUserPort addUserPort;
    @Inject
    UpdateUserPort updateUserPort;
    @Inject
    GetUserPort getUserPort;
    @Inject
    AddToUserCartPort addToUserCartPort;
    @Inject
    RemoveFromUserCartPort removeFromUserCartPort;

    @Override
    public List<User> getAllUsers() {
        return getUserPort.findAllUsers();
    }

    @Override
    public List<User> getAllUsersByLogin(String login) {
        return getUserPort.findAllByMatchingLogin(login);
    }

    @Override
    public User getUserById(UUID id) {
        return getUserPort.findUserById(id);
    }

    @Override
    public List<Order> getOngoingUserOrders(UUID id) {
        return getUserPort.findOngoingUserOrders(id);
    }

    @Override
    public List<Order> getFinishedUserOrders(UUID id) {
        return getUserPort.findOngoingUserOrders(id);
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        return updateUserPort.updateUser(id, updatedUser);
    }

    @Override
    public void changeUserPassword(UUID id, String currentPassword, String newPassword) {
        User user = getUserById(id);
        if (user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            updateUserPort.updateUser(id, user);
        }
    }

    @SneakyThrows
    @Override
    public Cart getUserCart(UUID id) {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    @Override
    public List<Order> getAllUserOrders(UUID userId) {
        return getUserPort.findUserOrders(userId);
    }

    @Override
    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        return addToUserCartPort.addToCart(userId, productId, quantity);
    }

    @Override
    public Cart removeFromCart(UUID userId, UUID productId) {
        return removeFromUserCartPort.removeFromCart(userId, productId);
    }

    @Override
    public User registerUser(User userToRegister) {
        return addUserPort.createUser(userToRegister);
    }

    @Override
    public User addUser(User user) {
        return addUserPort.createUser(user);
    }

    @Override
    public void suspendOrResumeUser(UUID userId) {
        User user = getUserById(userId);
        user.setSuspended(!user.isSuspended());
        updateUserPort.updateUser(userId, user);
    }
}
