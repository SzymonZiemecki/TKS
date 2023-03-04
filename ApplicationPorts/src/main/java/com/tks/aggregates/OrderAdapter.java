package com.tks.aggregates;

import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.orders.AddOrderPort;
import com.tks.infrastructure.orders.DeleteOrderPort;
import com.tks.infrastructure.orders.GetOrderPort;
import com.tks.infrastructure.orders.UpdateOrderPort;
import com.tks.model.Address;
import com.tks.model.CartItem;
import com.tks.model.Order;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import adapters.OrderRepository;
import adapters.UserRepository;
import data.model.OrderEnt;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import static com.tks.User.User.toUserDomainModel;
import static com.tks.model.Order.toOrderDomainModel;
import static data.utils.ErrorInfoEnt.*;

public class OrderAdapter implements AddOrderPort, DeleteOrderPort, GetOrderPort, UpdateOrderPort {

    @Inject
    OrderRepository orderRepository;

    @Inject
    UserRepository userRepository;

    @Override
    public Order createOrder(UUID userId, Address shippingAddress) {
        User customer = toUserDomainModel(userRepository.findById(userId).orElse(null));
        List<CartItem> orderItems = customer.getCart().getCartItems();
        Double orderValue = calculateOrderValue(orderItems);

        if (shouldCreateOrder(userId, orderItems, shippingAddress, orderValue)) {
            process(customer, orderItems, orderValue, shippingAddress);
            Order order = toOrderDomainModel(orderRepository.add(OrderEnt.toOrderEnt(new Order(customer, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(orderItems)))));
//            userRepository.clearUserCart(customer.getId());
            return order;
        } else {
            throw new BusinessLogicException(ORDER_VIOLATED_BUSINESS_LOGIC.getValue());
        }
    }

    @Override
    public void cancelOrder(UUID orderId) {
        orderRepository.delete(orderId);
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
                .forEach(orderEnt -> orders.add(toOrderDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public Order getOrderById(UUID id) {
        return toOrderDomainModel(orderRepository.findById(id).orElse(null));
    }

    @Override
    public List<Order> findOngoingOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findOngoingOrders().stream()
                .forEach(orderEnt -> orders.add(toOrderDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public List<Order> findFinishedOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findFinishedOrders().stream()
                .forEach(orderEnt -> orders.add(toOrderDomainModel(orderEnt)));
        return orders;
    }

    @Override
    public User findUserInOrder(UUID orderId) {
        return toUserDomainModel(orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())).getCustomer());
    }

    @Override
    public void deliverOrder(UUID orderId) {
        Order found = getOrderById(orderId);
        found.setDelivered(true);
        found.setDeliveryDate(new Date());
        orderRepository.update(found.getId(), OrderEnt.toOrderEnt(found));
    }

    private synchronized void process(User user, List<CartItem> products, Double orderValue, Address address) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.forEach((product) -> product.getProduct().setAvailableAmount((int) (product.getProduct().getAvailableAmount() - product.getQuantity())));
    }

    private boolean shouldCreateOrder(UUID userId, List<CartItem> orderItems, Address shippingAddress, Double orderValue) {
        if (isEnoughItems(orderItems) && !isUserSuspended(userId) && checkIfEnoughMoney(userId, orderValue)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEnoughItems(List<CartItem> orderItems) {
        return orderItems.stream()
                .map((orderItem -> isEnoughItems(orderItem.getProduct(), (long) orderItem.getProduct().getAvailableAmount())))
                .filter(result -> result.equals(true))
                .findAny()
                .orElseThrow(() -> new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
    }

    private boolean isUserSuspended(UUID userId) {
        User found = toUserDomainModel(userRepository.findById(userId).orElse(null));
        return Optional.ofNullable(found)
                .map(User::isSuspended)
                .filter(result -> result.equals(false))
                .orElseThrow(() -> new BusinessLogicException(ORDER_CUSTOMER_SUSPENDED.getValue()));
    }

    private boolean checkIfEnoughMoney(UUID userId, Double orderValue) {
        User found = toUserDomainModel(userRepository.findById(userId).orElse(null));
        return Optional.ofNullable(found)
                .map(user -> isEnoughMoney(user.getAccountBalance(), orderValue))
                .filter(result -> result.equals(true))
                .orElseThrow(() -> new BusinessLogicException(ORDER_INSUFFICIENT_FUNDS.getValue()));
    }

    private boolean isEnoughItems(Product product, Long availableAmount) {
        return product.getAvailableAmount() - availableAmount >= 0;
    }

    private boolean isEnoughMoney(Double userMoney, Double orderValue) {
        return userMoney - orderValue >= 0;
    }

    private Double calculateOrderValue(List<CartItem> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.forEach((cartItem) -> orderValue.updateAndGet(value -> value + cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        return orderValue.get();
    }
}
