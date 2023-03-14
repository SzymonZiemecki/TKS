package com.tks.security;

import com.tks.User.User;
import com.tks.model.Order;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryPort extends Repository<User> {
    List<Order> findUserOrders(UUID userId);
    List<Order> findOngoingUserOrders(UUID userId);
    List<Order> findFinishedUserOrders(UUID userId);
    User findOneByLogin(String login);
    List<User> findAllByMatchingLogin(String login);
}
