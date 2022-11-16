package com.pas.manager;

import com.pas.model.Address;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.repository.OrderRepository;
import com.pas.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class OrderManager {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private UserRepository userRepository;

    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(UUID userId, Address shippingAddress) {
        Optional<User> customer = userRepository.findById(userId);
        Map<Product, Long> orderItems = customer.get().getCart().getItems();
        Double orderValue = calculateOrderValue(orderItems);

        if (customer.isPresent() && shouldCreateOrder(userId, orderItems, shippingAddress, orderValue)) {
            process(customer.get(), orderItems, orderValue);
            Order order = orderRepository.add(new Order(customer.get(), shippingAddress, orderItems, new Date(), true, 0, false));
            clearUserCart(customer.get());
            return order;
        } else {
            throw new IllegalStateException("violated business logic");
        }
    }

    public void cancelOrder(UUID id) {
        orderRepository.delete(id);
    }

    public void deliverOrder(UUID orderId) {
        Optional<Order> found = orderRepository.findById(orderId);
        if (found.isPresent()) {
            found.get().setDelivered(true);
            found.get().setDeliveryDate(new Date());
            orderRepository.update(found.get().getId(), found.get());
        }
    }

    private void process(User user, Map<Product, Long> products, Double orderValue) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.entrySet().forEach(key -> {
            key.getKey().setAvailableAmount(key.getKey().getAvailableAmount() - key.getValue().intValue());
        });
    }

    private void clearUserCart(User user) {
        user.getCart().setItems(new HashMap<>());
        userRepository.update(user.getId(), user);
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
                .orElseThrow(() -> new IllegalStateException("Cant create order, items out of stock"));
    }

    private boolean isUserSuspended(UUID userId) {
        User found = userRepository.findById(userId).orElse(null);
        return Optional.ofNullable(found)
                .map(User::isSuspended)
                .filter(result -> result.equals(false))
                .orElseThrow(() -> new IllegalStateException("Cant create order, customer suspended"));
    }

    private boolean checkIfEnoughMoney(UUID userId, Double orderValue) {
        User found = userRepository.findById(userId).orElse(null);
        return Optional.ofNullable(found)
                .map(user -> isEnoughMoney(user.getAccountBalance(), orderValue))
                .filter(result -> result.equals(true))
                .orElseThrow(() -> new IllegalStateException("Cant create order, user don't have enough money"));
    }

    private boolean isEnoughItems(Product product, Long availableAmount) {
        return product.getAvailableAmount() - availableAmount >= 0;
    }

    private boolean isEnoughMoney(Double userMoney, Double orderValue) {
        return userMoney - orderValue >= 0;
    }

    private Double calculateOrderValue(Map<Product, Long> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.entrySet().forEach(key -> {
            orderValue.updateAndGet(v -> v + key.getKey().getPrice() * key.getValue());
        });
        return orderValue.get();
    }

    public void deleteOrder(UUID orderId) {
        User user = findUserInOrder(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("entity dont exist"));
        if(order.isDelivered()) {
            user.setAccountBalance(user.getAccountBalance() + calculateOrderValue(order.getItems()));
            orderRepository.delete(orderId);
        } else {
            throw new IllegalStateException("Cant delete ongoing order");
        }
    }

    public List<Order> findOngoingOrders() {
        return orderRepository.findOngoingOrders();
    }

    public List<Order> findFinishedOrders() {
        return orderRepository.findFinishedOrders();
    }

    public User findUserInOrder(UUID orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("entity dont exist")).getCustomer();
    }
}
