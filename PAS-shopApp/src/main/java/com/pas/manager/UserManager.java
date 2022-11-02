package com.pas.manager;

import com.pas.model.Address;
import com.pas.model.Product;
import com.pas.model.User;
import com.pas.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserManager {

    @Inject
    private UserRepository userRepository;

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User register(User user) {
        return userRepository.add(user);
    }

    public List<User> getAll () {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void addToCart(Product item, String login) {
        userRepository.;
    } //nie wiem jak to ma dzialac bo tak najwyrazniej nie

}
