package com.pas.repository;

import com.pas.exception.LoginAlreadyTakenException;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepository extends IRepositoryImpl<User> {

    @Inject
    OrderRepository orderRepository;

    @Override
    public synchronized User add(User entity) {
        if (!findByLogin(entity.getLogin()).isEmpty()) {
            throw new LoginAlreadyTakenException("Login already taken");
        } else {
            return super.add(entity);
        }
    }

    public List<User> findByLogin(String login) {
        return filter(user -> user.getLogin().contains(login));
    }

    public List<User> findOneByLogin(String login) {
        return new ArrayList<>(filter(user -> user.getLogin().equals(login)));
    }

    public List<Order> findUserOrders(UUID login) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(login));
    }

    @PostConstruct
    public void init() {
        User baseUser = new BaseUser("Szymon", "Ziemecki", "baseUser", "testpass", new Address("Poland", "Lodz", "Pomorska", "20", "90-001"), new Cart(), false, 200d);
        User manager = new Manager("Szymon", "Ziemecki", "manager", "testpass", new Address("Poland", "Lodz", "Pomorska", "20", "90-001"), new Cart(), false, 200d);
        User admin = new Admin("Szymon", "Ziemecki", "admin", "testpass", new Address("Poland", "Lodz", "Pomorska", "20", "90-001"), new Cart(), false, 200d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
    }
}
