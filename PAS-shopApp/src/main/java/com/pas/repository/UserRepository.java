package com.pas.repository;

import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepository extends IRepositoryImpl<User> {

    @Inject
    OrderRepository orderRepository;

    @Override
    public synchronized User add(User entity) {
        if (!findByLogin(entity.getLogin()).isEmpty()) {
            throw new EntityExistsException("Entity with given login already exist");
        } else {
            return super.add(entity);
        }
    }

    public List<User> findByLogin(String login) {
        return filter(user -> user.getLogin().contains(login));
    }

    public Optional<User> findOneByLogin(String login) {
        return filter(user -> user.getLogin().equals(login)).stream()
                .findFirst();
    }

    public List<Order> findUserOrders(UUID login) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(login));
    }

    @PostConstruct
    public void init() {
        Address initAddress = new Address("Poland", "Lodz", "Pomorska", "20", "90-001");
        User baseUser = new BaseUser("Szymon", "Ziemecki", "baseUser", "testpass", initAddress, new Cart(), false, 200d);
        User manager = new Manager("Szymon", "Ziemecki", "manager", "testpass", initAddress, new Cart(), false, 200d);
        User admin = new Admin("Szymon", "Ziemecki", "admin", "testpass", initAddress, new Cart(), false, 200d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
    }
}
