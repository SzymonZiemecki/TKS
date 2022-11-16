package com.pas.manager;

import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.repository.OrderRepository;
import com.pas.repository.ProductRepository;
import com.pas.repository.UserRepository;

import com.pas.utils.exceptionMessages.exMsg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class UserManager {

    @Inject
    private UserRepository userRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private OrderRepository orderRepository;

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString()));
    }

    public List<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findOneByLogin(String login) {
        return userRepository.findOneByLogin(login).orElseThrow(() -> new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString()));
    }

    public List<Order> findOngoingUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(order -> !order.isDelivered())
                .collect(Collectors.toList());
    }

    public List<Order> findFinishedUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(Order::isDelivered)
                .collect(Collectors.toList());
    }

    public User register(User user) {
        return userRepository.add(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, User user) {
        return userRepository.update(id, user);
    }

    public void suspendOrResumeUser(UUID userId, boolean suspendOrResume) {
        Optional<User> found = userRepository.findById(userId);
        if (found.isPresent()) {
            found.get().setSuspended(suspendOrResume);
            userRepository.update(found.get().getId(), found.get());
        } else {
            throw new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString());
        }
    }

    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            found.get().getCart().getItems().put(product.get(), quantity);
            return found.get().getCart();
        } else {
            throw new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString());
        }
    }

    public Cart removeFromCart(UUID userId, UUID productId) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            Map<Product, Long> itemsInCart = found.get().getCart().getItems();
            itemsInCart.remove(product.get());
            found.get().getCart().setItems(itemsInCart);
            return found.get().getCart();
        } else {
            throw new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString());
        }
    }

    public List<Order> findUserOrders(UUID userId){
        return userRepository.findUserOrders(userId);
    }

}
