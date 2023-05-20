package com.tks.microservices.userservice.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

import com.tks.microservices.userservice.repository.api.UserRepository;
import com.tks.microservices.userservice.repository.exception.LoginAlreadyTakenException;
import com.tks.microservices.userservice.repository.model.AddressEnt;
import com.tks.microservices.userservice.repository.model.BaseUserEnt;
import com.tks.microservices.userservice.repository.model.UserEnt;
import com.tks.microservices.userservice.repository.model.ManagerEnt;
import com.tks.microservices.userservice.repository.model.AdminEnt;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.SneakyThrows;

@Component
public class UserEntRepository extends RepositoryImpl<UserEnt> implements UserRepository {

    @SneakyThrows
    @Override
    public synchronized UserEnt add(UserEnt entity) {
        if (!findAllByMatchingLogin(entity.getLogin()).isEmpty()) {
            throw new LoginAlreadyTakenException("Login already taken");
        } else {
            return super.add(entity);
        }
    }

    @Override
    public UserEnt findOneByLogin(String login) {
        return filter(user -> user.getLogin().equals(login)).stream().findFirst().orElseThrow(NotFoundException::new);
    }

    @Override
    public List<UserEnt> findAllByMatchingLogin(String login) {
        return filter(user -> user.getLogin().equals(login));
    }

    @PostConstruct
    public void init() {
        UserEnt baseUser = new BaseUserEnt("Szymon", "Ziemecki", "baseUser", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), false, 200d);
        UserEnt manager = new ManagerEnt("Szymon", "Ziemecki", "manager", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), false, 200d);
        UserEnt admin = new AdminEnt("Szymon", "Ziemecki", "admin", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), false, 200d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
    }
}
