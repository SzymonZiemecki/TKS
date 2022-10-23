package com.pas.repository;

import com.pas.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository extends IRepositoryImpl<User> {

    public Optional<User> findByLogin(String login){
        return filter(user -> user.getLogin().equals(login)).stream().findFirst();
    }
}
