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

import static com.tks.mapper.EntityModelMapper.*;

@ApplicationScoped
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    @Inject
    OrderEntRepository orderRepository;

    @Override
    public List<Order> findOngoingOrders() {
        return listToDomainModel(orderRepository.findOngoingOrders());
    }

    @Override
    public List<Order> findFinishedOrders() {
        return listToDomainModel(orderRepository.findFinishedOrders());
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
        return listToDomainModel(orderRepository.findAll());
    }

    @Override
    public int size() {
        return orderRepository.size();
    }

    public List<Order> filter(Predicate<OrderEnt> predicate) {
        return listToDomainModel(orderRepository.filter(predicate));
    }
}
