package com.tks.services;

import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.orders.AddOrderPort;
import com.tks.infrastructure.orders.DeleteOrderPort;
import com.tks.infrastructure.orders.GetOrderPort;
import com.tks.infrastructure.orders.UpdateOrderPort;
import com.tks.infrastructure.users.GetUserPort;
import com.tks.infrastructure.users.UpdateUserPort;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;
import com.tks.userinterface.OrderUseCase;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static data.utils.ErrorInfoEnt.*;

@ApplicationScoped
public class OrderService implements OrderUseCase {
    @Inject
    AddOrderPort addOrderPort;
    @Inject
    DeleteOrderPort deleteOrderPort;
    @Inject
    GetOrderPort getOrderPort;
    @Inject
    UpdateOrderPort updateOrderPort;
    @Inject
    GetUserPort getUserPort;
    @Inject
    UpdateUserPort updateUserPort;

    @Override
    public List<Order> getAllOrders() {
        return getOrderPort.getAllOrders();
    }

    @Override
    public Order getOrderById(UUID id) {
        return getOrderPort.getOrderById(id);
    }

    @Override
    public List<Order> getOngoingOrders() {
        return getOrderPort.findOngoingOrders();
    }

    @Override
    public List<Order> getFinishedOrders() {
        return getOrderPort.findFinishedOrders();
    }

    @Override
    public Order createOrder(UUID userId, Address shippingAddress) {
        User customer = getUserPort.findUserById(userId);
        List<CartItem> orderItems = customer.getCart().getCartItems();
        Double orderValue = calculateOrderValue(orderItems);

        if (shouldCreateOrder(userId, orderItems, shippingAddress, orderValue)) {
            process(customer, orderItems, orderValue, shippingAddress);
            Order order = addOrderPort.createOrder(new Order(customer, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(orderItems)));
            clearUserCart(customer.getId());
            return order;
        } else {
            throw new BusinessLogicException(ORDER_VIOLATED_BUSINESS_LOGIC.getValue());
        }
    }

    @Override
    public void deliverOrder(UUID id) {
        Order found = getOrderById(id);
        found.setDelivered(true);
        found.setDeliveryDate(new Date());
        updateOrderPort.updateOrder(found.getId(), found);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        if (order.isDelivered()) {
            deleteOrderPort.deleteOrder(orderId);
        } else {
            throw new BusinessLogicException(ORDER_DELETE_ONGOING_ERROR.getValue());
        }
    }

    private Double calculateOrderValue(List<CartItem> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.forEach((cartItem) -> orderValue.updateAndGet(value -> value + cartItem.getProduct().getPrice() * cartItem.getQuantity()));
        return orderValue.get();
    }

    private boolean shouldCreateOrder(UUID userId, List<CartItem> orderItems, Address shippingAddress, Double orderValue) {
        return isEnoughItems(orderItems) && !isUserSuspended(userId) && checkIfEnoughMoney(userId, orderValue);
    }

    private boolean isEnoughItems(List<CartItem> orderItems) {
        return orderItems.stream()
                .map((orderItem -> isEnoughItems(orderItem.getProduct(), (long) orderItem.getProduct().getAvailableAmount())))
                .filter(result -> result.equals(true))
                .findAny()
                .orElseThrow(() -> new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
    }

    private boolean isEnoughItems(Product product, Long availableAmount) {
        return product.getAvailableAmount() - availableAmount >= 0;
    }

    private boolean isUserSuspended(UUID userId) {
        User found = getUserPort.findUserById(userId);
        return Optional.ofNullable(found)
                .map(User::isSuspended)
                .filter(result -> result.equals(false))
                .orElseThrow(() -> new BusinessLogicException(ORDER_CUSTOMER_SUSPENDED.getValue()));
    }

    private boolean checkIfEnoughMoney(UUID userId, Double orderValue) {
        User found = getUserPort.findUserById(userId);
        return Optional.ofNullable(found)
                .map(user -> isEnoughMoney(user.getAccountBalance(), orderValue))
                .filter(result -> result.equals(true))
                .orElseThrow(() -> new BusinessLogicException(ORDER_INSUFFICIENT_FUNDS.getValue()));
    }

    private boolean isEnoughMoney(Double userMoney, Double orderValue) {
        return userMoney - orderValue >= 0;
    }

    private synchronized void process(User user, List<CartItem> products, Double orderValue, Address address) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.forEach((product) -> product.getProduct().setAvailableAmount((int) (product.getProduct().getAvailableAmount() - product.getQuantity())));
    }

    private void clearUserCart(UUID userId){
        User user = getUserPort.findUserById(userId);
        user.setCart(new Cart(new ArrayList<>()));
        updateUserPort.updateUser(userId, user);
    }
}
