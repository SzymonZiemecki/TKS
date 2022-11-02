package com.pas.manager;

import com.pas.model.Order;
import com.pas.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderManager {

    @Inject
    private OrderRepository orderRepository;

    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    public Order register(Order order){
        return orderRepository.add(order);
    }

    public Order createOrder(Order order) { return orderRepository.add(order);}

    public void cancelOrder(UUID id) { orderRepository.delete(id);}

}
