package com.tks.aggregates;

import com.tks.User.User;
import com.tks.security.UserRepositoryPort;
import com.tks.model.Order;
import com.tks.data.user.UserEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.tks.repository.UserEntRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static com.tks.mapper.EntityModelMapper.*;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    UserEntRepository userRepository;

    @Override
    public User add(User entity) {
        return toDomainModel(userRepository.add(toEntModel(entity)));
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(id);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete((UserEnt) toEntModel(entity));
    }

    @Override
    public User update(UUID id, User entity) {
        return toDomainModel(userRepository.update(id, toEntModel(entity)));
    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return toDomainModel(userRepository.findById(id));
    }

    @Override
    public List<User> findAll() {
        return listToDomainModel(userRepository.findAll());
    }

    @Override
    public int size() {
        return userRepository.size();
    }

    public List<User> filter(Predicate<UserEnt> predicate) {
        return listToDomainModel(userRepository.filter(predicate));
    }

    @Override
    public List<Order> findUserOrders(UUID userId) {
        return listToDomainModel(userRepository.findUserOrders(userId));
    }

    @Override
    public List<Order> findOngoingUserOrders(UUID userId) {
        return listToDomainModel(userRepository.findOngoingUserOrders(userId));
    }

    @Override
    public List<Order> findFinishedUserOrders(UUID userId) {
        return listToDomainModel(userRepository.findFinishedUserOrders(userId));
    }

    @Override
    public User findOneByLogin(String login) {
        return toDomainModel(userRepository.findOneByLogin(login));
    }

    @Override
    public List<User> findAllByMatchingLogin(String login) {
        return listToDomainModel(userRepository.findAllByMatchingLogin(login));
    }
}
