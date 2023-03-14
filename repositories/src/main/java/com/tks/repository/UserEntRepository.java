package com.tks.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tks.api.UserRepository;
import com.tks.data.model.AddressEnt;
import com.tks.data.model.OrderEnt;
import com.tks.data.user.UserEnt;
import com.tks.data.user.AdminEnt;
import com.tks.data.user.BaseUserEnt;
import com.tks.data.user.CartEnt;
import com.tks.data.user.ManagerEnt;
import com.tks.exception.LoginAlreadyTakenException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;


@ApplicationScoped
public class UserEntRepository extends RepositoryImpl<UserEnt> implements UserRepository {

    @Inject
    OrderEntRepository orderRepository;

    @SneakyThrows
    @Override
    public synchronized UserEnt add(UserEnt entity) {
        if (!findAllByMatchingLogin(entity.getLogin()).isEmpty()) {
            throw new LoginAlreadyTakenException("Login already taken");
        } else {
            return super.add(entity);
        }
    }


    @PostConstruct
    public void init() {
        UserEnt baseUser = new BaseUserEnt("Szymon", "Ziemecki", "baseUser", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), new CartEnt(), false, 200d);
        UserEnt manager = new ManagerEnt("Szymon", "Ziemecki", "manager", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), new CartEnt(), false, 200d);
        UserEnt admin = new AdminEnt("Szymon", "Ziemecki", "admin", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), new CartEnt(), false, 200d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
    }

    @Override
    public List<OrderEnt> findUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId));
    }

    @Override
    public List<OrderEnt> findOngoingUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream()
                .filter(orderEnt -> !orderEnt.isDelivered())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderEnt> findFinishedUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream()
                .filter(orderEnt -> orderEnt.isDelivered())
                .collect(Collectors.toList());
    }

    @Override
    public UserEnt findOneByLogin(String login) {
        return filter(user -> user.getLogin().equals(login)).stream().findFirst().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserEnt> findAllByMatchingLogin(String login) {
        return filter(user -> user.getLogin().equals(login));
    }
}
