package com.tks.microservices.userservice.core.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.port.security.UserRepositoryPort;
import com.tks.microservices.userservice.port.ui.UserService;
import com.tks.microservices.userservice.repository.api.UserRepository;
import com.tks.microservices.userservice.repository.model.security.CustomUserDetails;
import com.tks.microservices.userservice.repository.model.security.Permission;
import com.tks.microservices.userservice.rest.dto.AddressDTO;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;

import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;

@Service
public class UserServiceImpl implements UserService {

    private UserRepositoryPort userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepositoryPort userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByLogin(String login) {
        return userRepository.findAllByMatchingLogin(login);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(UUID id, UpdateUserDTO dto) {
        User updatedUser = userRepository.findById(id);
        updatedUser.setFirstName(dto.getFirstName());
        updatedUser.setLastName(dto.getLastName());
        updatedUser.setAddress(AddressDTO.addressDTOToDomainModel(dto.getAddress()));
        updatedUser.setAccountBalance(dto.getAccountBalance());
        return userRepository.update(id, updatedUser);
    }

    @Override
    public void changeUserPassword(UUID id, String currentPassword, String newPassword) {
        User user = getUserById(id);
        if (user.getPassword().equals(currentPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.update(id, user);
        }
    }


    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSuspended(false);
        return userRepository.add(user);
    }

    @Override
    public void suspendOrResumeUser(UUID userId) {
        User user = getUserById(userId);
        user.setSuspended(!user.isSuspended());
        userRepository.update(userId, user);
    }

    public CustomUserDetails findUserForAuthorization(String login) {
        User user = userRepository.findOneByLogin(login);
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setLogin(user.getLogin());
        userDetails.setUserPassword(user.getPassword());
        userDetails.setAuthority(user.getClass().getSimpleName());
        userDetails.setActive(!user.isSuspended());
        return userDetails;
    }
}
