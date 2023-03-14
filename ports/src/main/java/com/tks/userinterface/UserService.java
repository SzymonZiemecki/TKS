package com.tks.userinterface;

import com.tks.User.User;
import com.tks.model.Cart;
import com.tks.model.Order;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    List<User> getAllUsersByLogin(String login);
    User findUserByLogin(String login);
    User getUserById(UUID id);
    List<Order> getOngoingUserOrders(UUID id);
    List<Order> getFinishedUserOrders(UUID id);
    User updateUser(UUID id, User updatedUser);
    void changeUserPassword(UUID id, String currentPassword, String newPassword);
    Cart getUserCart(UUID id);
    List<Order> getAllUserOrders(UUID userId);
    Cart addToCart(UUID userId, UUID productId, Long quantity);
    Cart removeFromCart(UUID userId, UUID productId);
    User addUser(User user);
    void suspendOrResumeUser(UUID userId);


}
