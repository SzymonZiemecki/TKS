package com.tks.services;


import com.tks.infrastructure.carts.AddToUserCartPort;
import com.tks.infrastructure.carts.RemoveFromUserCartPort;
import com.tks.infrastructure.users.AddUserPort;
import com.tks.infrastructure.users.GetUserPort;
import com.tks.infrastructure.users.UpdateUserPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    private AddUserPort addUserPort;
    @Inject
    UpdateUserPort updateUserPort;
    @Inject
    GetUserPort getUserPort;
    @Inject
    AddToUserCartPort addToUserCartPort;
    @Inject
    RemoveFromUserCartPort removeFromUserCartPort;
}
