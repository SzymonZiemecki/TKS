package com.tks.microservices.userservice.port.ui;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.userservice.core.model.User;


public interface UserService {
    List<User> getAllUsers();
    List<User> getAllUsersByLogin(String login);
    User findUserByLogin(String login);
    User getUserById(UUID id);
    User updateUser(UUID id, User updatedUser);
    void changeUserPassword(UUID id, String currentPassword, String newPassword);
    User addUser(User user);
    void suspendOrResumeUser(UUID userId);
}
