package com.tks.api;

import com.tks.data.model.OrderEnt;
import com.tks.data.user.UserEnt;
import com.tks.infrastructure.Repository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends Repository<UserEnt> {
    List<OrderEnt> findUserOrders(UUID userId);
    List<OrderEnt> findOngoingUserOrders(UUID userId);
    List<OrderEnt> findFinishedUserOrders(UUID userId);
    UserEnt findOneByLogin(String login);
    List<UserEnt> findAllByMatchingLogin(String login);
}
