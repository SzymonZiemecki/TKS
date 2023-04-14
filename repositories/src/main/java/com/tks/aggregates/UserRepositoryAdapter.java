package com.tks.aggregates;

import com.tks.User.User;
import com.tks.data.model.OrderEnt;
import com.tks.data.product.ProductEnt;
import com.tks.security.UserRepositoryPort;
import com.tks.model.Order;
import com.tks.data.user.UserEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.tks.repository.UserEntRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    UserEntRepository userRepository;

    @Override
    public User add(User entity) {
        return UserEnt.userEntToDomainModel(userRepository.add(UserEnt.userToEnt(entity)));
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(id);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(UserEnt.userToEnt(entity));
    }

    @Override
    public User update(UUID id, User entity) {
        return UserEnt.userEntToDomainModel(userRepository.update(id, UserEnt.userToEnt(entity)));
    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public User findById(UUID id) {
        return UserEnt.userEntToDomainModel(userRepository.findById(id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserEnt::userEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return userRepository.size();
    }

    public List<User> filter(Predicate<UserEnt> predicate) {
        return userRepository.filter(predicate)
                .stream()
                .map(UserEnt::userEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findUserOrders(UUID userId) {
        return userRepository.findUserOrders(userId)
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findOngoingUserOrders(UUID userId) {
        return userRepository.findOngoingUserOrders(userId)
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findFinishedUserOrders(UUID userId) {
        return userRepository.findFinishedUserOrders(userId)
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public User findOneByLogin(String login) {
        return UserEnt.userEntToDomainModel(userRepository.findOneByLogin(login));
    }

    @Override
    public List<User> findAllByMatchingLogin(String login) {
        return userRepository.findAllByMatchingLogin(login)
                .stream()
                .map(UserEnt::userEntToDomainModel)
                .collect(Collectors.toList());
    }
}
