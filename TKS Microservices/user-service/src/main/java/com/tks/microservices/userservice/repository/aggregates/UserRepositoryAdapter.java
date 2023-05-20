package com.tks.microservices.userservice.repository.aggregates;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.port.security.UserRepositoryPort;
import com.tks.microservices.userservice.repository.model.UserEnt;
import com.tks.microservices.userservice.repository.UserEntRepository;


@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private UserEntRepository userRepository;

    public UserRepositoryAdapter(final UserEntRepository userRepository) {
        this.userRepository = userRepository;
    }

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
