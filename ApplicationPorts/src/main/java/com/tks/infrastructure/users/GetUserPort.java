package com.tks.infrastructure.users;

import com.tks.User.User;
import com.tks.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetUserPort {

    User findUserById(UUID userId);
    List<User> findAllUsers();
    List<Order> findUserOrders(UUID userId);
    List<Order> findOngoingUserOrders(UUID userId);
    List<Order> findFinishedUserOrders(UUID userId);
    User findOneByLogin(String login);
    List<User> findAllByMatchingLogin(String login);
}
