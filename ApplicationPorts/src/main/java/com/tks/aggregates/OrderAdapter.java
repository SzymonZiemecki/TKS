package com.tks.aggregates;

import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.orders.AddOrderPort;
import com.tks.infrastructure.orders.DeleteOrderPort;
import com.tks.infrastructure.orders.GetOrderPort;
import com.tks.infrastructure.orders.UpdateOrderPort;
import com.tks.mapper.ModelMapperBean;
import com.tks.model.Address;
import com.tks.model.CartItem;
import com.tks.model.Order;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;
import repository.OrderEntRepository;
import repository.UserEntRepository;
import data.model.OrderEnt;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import static data.utils.ErrorInfoEnt.*;

@ApplicationScoped
public class OrderAdapter implements AddOrderPort, DeleteOrderPort, GetOrderPort, UpdateOrderPort {

    @Inject
    OrderEntRepository orderRepository;

    @Inject
    UserEntRepository userRepository;

    @Inject
    ModelMapperBean mapper;

    @Override
    public Order createOrder(Order order) {
        return ModelMapperBean.toDomainModel(orderRepository.add(ModelMapperBean.toEntModel(order)));
    }

    @Override
    public void deleteOrder(UUID orderId) {
        User user = findUserInOrder(orderId);
        Order order = getOrderById(orderId);
        if (order.isDelivered()) {
            orderRepository.delete(orderId);
        } else {
            throw new BusinessLogicException(ORDER_DELETE_ONGOING_ERROR.getValue());
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().stream()
                .forEach(orderEnt -> orders.add(mapper.toDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public Order getOrderById(UUID id) {
        return ModelMapperBean.toDomainModel(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())));
    }

    @Override
    public List<Order> findOngoingOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findOngoingOrders().stream()
                .forEach(orderEnt -> orders.add(ModelMapperBean.toDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public List<Order> findFinishedOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findFinishedOrders().stream()
                .forEach(orderEnt -> orders.add(mapper.toDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public User findUserInOrder(UUID orderId) {
        return mapper.toDomainModel(orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())).getCustomer());
    }

    @Override
    public void updateOrder(UUID orderId, Order order) {
        Order user = getOrderById(order.getId());
        ModelMapperBean.toDomainModel(orderRepository.update(orderId, ModelMapperBean.toEntModel(order)));
    }
}
