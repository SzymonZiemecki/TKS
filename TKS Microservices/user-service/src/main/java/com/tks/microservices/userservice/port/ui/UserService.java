package com.tks.microservices.userservice.port.ui;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.repository.model.security.CustomUserDetails;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;
import com.tks.microservices.userservice.rest.dto.UserDTO;


public interface UserService {
    List<User> getAllUsers();
    List<User> getAllUsersByLogin(String login);
    User findUserByLogin(String login);
    User getUserById(UUID id);
    User updateUser(UUID id, UpdateUserDTO updatedUser);
    void changeUserPassword(UUID id, String currentPassword, String newPassword);
    User addUser(User user);
    void suspendOrResumeUser(UUID userId);
    CustomUserDetails findUserForAuthorization(String login);
}
