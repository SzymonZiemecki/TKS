package com.tks.services;

import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.exception.BusinessLogicException;
import com.tks.security.OrderRepositoryPort;
import com.tks.security.UserRepositoryPort;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;
import com.tks.userinterface.OrderService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import static com.tks.data.utils.ErrorInfoEnt.*;
import static com.tks.utils.Assertions.assertTrue;

@ApplicationScoped
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Inject
    OrderRepositoryPort orderRepository;
    @Inject
    UserRepositoryPort userRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Order> getOngoingOrders() {
        return orderRepository.findOngoingOrders();
    }

    @Override
    public List<Order> getFinishedOrders() {
        return orderRepository.findFinishedOrders();
    }

    @Override
    @Transactional
    public Order createOrder(UUID userId, Address shippingAddress) {
        User customer = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        List<CartItem> orderItems = customer.getCart().getCartItems();
        Double orderValue = calculateOrderValue(orderItems);

        assertCanCreateOrder(customer, orderItems, orderValue);
        process(customer, orderItems, orderValue, shippingAddress);
        Order order = orderRepository.add(new Order(customer, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(orderItems)));
        clearUserCart(customer);
        return order;
    }

    private void assertCanCreateOrder(User customer, List<CartItem> orderItems, Double orderValue) {
        assertTrue(this::isEnoughItems, orderItems, new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
        isEnoughItems(orderItems);
        isUserSuspended(customer);
        checkIfEnoughMoney(customer, orderValue);
    }

    @Override
    public void deliverOrder(UUID id) {
        Order found = getOrderById(id);
        found.setDelivered(true);
        found.setDeliveryDate(new Date());
        orderRepository.update(found.getId(), found);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        if (order.isDelivered()) {
            orderRepository.delete(orderId);
        } else {
            throw new BusinessLogicException(ORDER_DELETE_ONGOING_ERROR.getValue());
        }
    }

    private Double calculateOrderValue(List<CartItem> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.forEach((cartItem) -> orderValue.updateAndGet(value -> value + cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        return orderValue.get();
    }

    private Boolean isEnoughItems(List<CartItem> orderItems) {
        return orderItems.stream()
                .map((orderItem -> isEnoughItemz(orderItem.getProduct(), orderItem.getQuantity())))
                .allMatch(res -> res.equals(true));
    }

    private Boolean isEnoughItemz(Product product, Long availableAmount) {
        return product.getAvailableAmount() - availableAmount >= 0;
    }

    private void isUserSuspended(User user) {
        Predicate<User> isUserNotSuspended = (u) -> !u.isSuspended();
        assertTrue(isUserNotSuspended, user, new BusinessLogicException(ORDER_CUSTOMER_SUSPENDED.getValue()));
    }

    private void checkIfEnoughMoney(User user, Double orderValue) {
        BiPredicate<User, Double> isEnoughMoney = (u, v) -> u.getAccountBalance() - v >= 0;
        assertTrue(isEnoughMoney, user, orderValue, new BusinessLogicException(ORDER_INSUFFICIENT_FUNDS.getValue()));
    }

    private synchronized void process(User user, List<CartItem> products, Double orderValue, Address address) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.forEach((product) -> product.getProduct().setAvailableAmount((int) (product.getProduct().getAvailableAmount() - product.getQuantity())));
    }

    private void clearUserCart(User user) {
        user.setCart(new Cart(new ArrayList<>()));
        userRepository.update(user.getId(), user);
    }
}
