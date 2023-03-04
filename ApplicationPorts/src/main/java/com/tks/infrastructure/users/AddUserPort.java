package com.tks.infrastructure.users;

import com.tks.User.User;

public interface AddUserPort {

    User createUser(User user);
}
