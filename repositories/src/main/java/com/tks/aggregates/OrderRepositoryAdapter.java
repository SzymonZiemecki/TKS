package com.tks.aggregates;

import com.tks.security.OrderRepositoryPort;
import com.tks.model.Order;
import com.tks.data.model.OrderEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.tks.repository.OrderEntRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@ApplicationScoped
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    @Inject
    OrderEntRepository orderRepository;

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
        return orderRepository.findById(UUID.fromString(id)).isPresent();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.of(OrderEnt.orderEntToDomainModel(orderRepository.findById(id).get()));
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
