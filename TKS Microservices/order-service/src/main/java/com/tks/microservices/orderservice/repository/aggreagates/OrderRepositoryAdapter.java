package com.tks.microservices.orderservice.repository.aggreagates;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tks.microservices.orderservice.core.model.Order;
import com.tks.microservices.orderservice.port.security.OrderRepositoryPort;
import com.tks.microservices.orderservice.repository.OrderEntRepository;
import com.tks.microservices.orderservice.repository.model.OrderEnt;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;


@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private OrderEntRepository orderRepository;

    public OrderRepositoryAdapter(final OrderEntRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findOngoingOrders() {
        return orderRepository.findOngoingOrders()
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findFinishedOrders() {
        return orderRepository.findFinishedOrders()
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Order add(Order entity) {
        return OrderEnt.orderEntToDomainModel(orderRepository.add(OrderEnt.orderToEnt(entity)));
    }

    @Override
    public void delete(UUID id) {
        orderRepository.delete(id);
    }

    @Override
    public void delete(Order entity) {
        orderRepository.delete(OrderEnt.orderToEnt(entity));
    }

    @Override
    public Order update(UUID id, Order entity) {
        return OrderEnt.orderEntToDomainModel(orderRepository.update(id, OrderEnt.orderToEnt(entity)));
    }

    @Override
    public boolean exists(String id) {
        try {
            orderRepository.findById(UUID.fromString(id));
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    @Override
    public Order findById(UUID id) {
        return OrderEnt.orderEntToDomainModel(orderRepository.findById(id));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return orderRepository.size();
    }

    public List<Order> filter(Predicate<OrderEnt> predicate) {
        return orderRepository.filter(predicate)
                .stream()
                .map(OrderEnt::orderEntToDomainModel)
                .collect(Collectors.toList());
    }
}
