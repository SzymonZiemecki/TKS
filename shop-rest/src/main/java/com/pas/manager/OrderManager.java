package com.pas.manager;

import com.pas.model.Address;
import com.pas.model.Order;
import com.pas.model.User.User;
import com.pas.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import com.pas.exception.BusinessLogicException;
import com.pas.model.Product.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.pas.utils.ErrorInfo.*;


@ApplicationScoped
public class OrderManager {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private UserManager userManager;

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(UUID userId, Address shippingAddress) {
        User customer = userManager.findById(userId);
        Map<Product, Long> orderItems = customer.getCart().getItems();
        Double orderValue = calculateOrderValue(orderItems);

        if (shouldCreateOrder(userId, orderItems, shippingAddress, orderValue)) {
            process(customer, orderItems, orderValue, shippingAddress);
            Order order = orderRepository.add(new Order(customer, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(orderItems)));
            userManager.clearUserCart(customer.getId());
            return order;
        } else {
            throw new BusinessLogicException(ORDER_VIOLATED_BUSINESS_LOGIC.getValue());
        }
    }

    public void cancelOrder(UUID id) {
        orderRepository.delete(id);
    }

    public void deliverOrder(UUID orderId) {
        Order found = findById(orderId);
        found.setDelivered(true);
        found.setDeliveryDate(new Date());
        orderRepository.update(found.getId(), found);
    }

    private synchronized void process(User user, Map<Product, Long> products, Double orderValue, Address address) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.forEach((product, quantity) -> product.setAvailableAmount(product.getAvailableAmount() - quantity.intValue()));
    }

    private boolean shouldCreateOrder(UUID userId, Map<Product, Long> orderItems, Address shippingAddress, Double orderValue) {
        if (isEnoughItems(orderItems) && !isUserSuspended(userId) && checkIfEnoughMoney(userId, orderValue)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEnoughItems(Map<Product, Long> orderItems) {
        return orderItems.entrySet().stream()
                .map((entry -> isEnoughItems(entry.getKey(), entry.getValue())))
                .filter(result -> result.equals(true))
                .findAny()
                .orElseThrow(() -> new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
    }

    private boolean isUserSuspended(UUID userId) {
        User found = userManager.findById(userId);
        return Optional.ofNullable(found)
                .map(User::isSuspended)
                .filter(result -> result.equals(false))
                .orElseThrow(() -> new BusinessLogicException(ORDER_CUSTOMER_SUSPENDED.getValue()));
    }

    private boolean checkIfEnoughMoney(UUID userId, Double orderValue) {
        User found = userManager.findById(userId);
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

    private Double calculateOrderValue(Map<Product, Long> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.forEach((product, quantity) -> orderValue.updateAndGet(value -> value + product.getPrice() * quantity));
        return orderValue.get();
    }

    public void deleteOrder(UUID orderId) {
        User user = findUserInOrder(orderId);
        Order order = findById(orderId);
        if(order.isDelivered()) {
            user.setAccountBalance(user.getAccountBalance() + calculateOrderValue(order.getItems()));
            orderRepository.delete(orderId);
        } else {
            throw new BusinessLogicException(ORDER_DELETE_ONGOING_ERROR.getValue());
        }
    }

    public List<Order> findOngoingOrders() {
        return orderRepository.findOngoingOrders();
    }

    public List<Order> findFinishedOrders() {
        return orderRepository.findFinishedOrders();
    }

    public User findUserInOrder(UUID orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())).getCustomer();
    }
}
