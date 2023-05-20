package com.tks.microservices.userservice.repository.api;

import java.util.List;

import com.tks.microservices.userservice.port.security.Repository;
import com.tks.microservices.userservice.repository.model.UserEnt;


public interface UserRepository extends Repository<UserEnt> {
    UserEnt findOneByLogin(String login);
    List<UserEnt> findAllByMatchingLogin(String login);
}
