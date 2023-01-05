package com.pas.manager;

import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.repository.OrderRepository;
import com.pas.repository.ProductRepository;
import com.pas.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;

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
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<User> findOneByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public List<Order> findOngoingUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream().filter(order -> !order.isDelivered()).collect(Collectors.toList());
    }

    public List<Order> findFinishedUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream().filter(Order::isDelivered).collect(Collectors.toList());
    }

    public User register(User user) {
        return userRepository.add(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, User updatedUser) {
        User user = findById(updatedUser.getId());
        user.setPassword(updatedUser.getPassword());
        user.setAccountBalance(updatedUser.getAccountBalance());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        return userRepository.update(id, updatedUser);
    }

    public void suspendOrResumeUser(UUID userId, boolean suspendOrResume) {
        Optional<User> found = userRepository.findById(userId);
        if (found.isPresent()) {
            found.get().setSuspended(suspendOrResume);
            userRepository.update(found.get().getId(), found.get());
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            found.get().getCart().getItems().put(product.get(), quantity);
            return found.get().getCart();
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
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
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    public List<Order> findUserOrders(UUID userId) {
        return userRepository.findUserOrders(userId);
    }

    public List<User> findUsers(Optional<String> allMatchingByLogin, Optional<String> oneByLogin) {
        if (allMatchingByLogin.isPresent()) {
            return userRepository.findByLogin(allMatchingByLogin.get());
        } else if (oneByLogin.isPresent()) {
            return userRepository.findOneByLogin(oneByLogin.get());
        } else {
            return findAllUsers();
        }
    }
    public void clearUserCart(UUID userID){
        User user = findById(userID);
        user.getCart().setItems(new HashMap<>());
        userRepository.update(user.getId(), user);
    }
}
