package com.pas.repository;

import com.pas.model.Order;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository extends IRepositoryImpl<User> {

    @Inject
    OrderRepository orderRepository;

    @Override
    public synchronized User add(User entity) {
        if(findByLogin(entity.getLogin()).isPresent()){
            throw new EntityExistsException("Entity with given login already exist");
        } else {
            return super.add(entity);
        }
    }

    public Optional<User> findByLogin(String login){
        return filter(user -> user.getLogin().equals(login)).stream().findFirst();
    }

    public List<Order> findUserOrders(String login){
        return orderRepository.filter(order -> order.getCustomer().getLogin().equals(login));

    }
}
