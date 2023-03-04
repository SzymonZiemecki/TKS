package com.tks.infrastructure.users;

import com.tks.User.User;

import java.util.UUID;

public interface UpdateUserPort {

    User updateUser(UUID userId, User updatedUser);
}
