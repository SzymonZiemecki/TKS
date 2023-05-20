package com.tks.microservices.userservice.port.security;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.userservice.core.model.User;


public interface UserRepositoryPort extends Repository<User> {
    User findOneByLogin(String login);
    List<User> findAllByMatchingLogin(String login);
}
