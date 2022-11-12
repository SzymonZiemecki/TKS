package com.pas.manager;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.repository.OrderRepository;
import com.pas.repository.ProductRepository;
import com.pas.repository.UserRepository;
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
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with given ID doesnt exist"));
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Entity with given ID doesnt exist"));
    }
    public List<Order> findOngoingUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(order -> order.isDelivered() == false)
                .collect(Collectors.toList());
    }
    public List<Order> findFinishedUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(order -> order.isDelivered() == true)
                .collect(Collectors.toList());
    }
    public User register(User user) {
        return userRepository.add(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(UUID id, User user) {
        userRepository.update(id, user);
    }

    public void deleteUser(UUID userId) {
        userRepository.delete(userId);
    }

    public void suspendOrResumeUser(UUID userId, boolean suspendOrResume) {
        Optional<User> found = userRepository.findById(userId);
        if (found.isPresent()) {
            found.get().setSuspended(suspendOrResume);
        }
        userRepository.update(found.get().getId(), found.get());
    }

    public void addToCart(UUID userId, UUID productId, Long quantity) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            found.get().getCart().getItems().put(product.get(), quantity);
        }
    }

    public void removeFromCart(UUID userId, UUID productId) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            Map<Product, Long> itemsInCart = found.get().getCart().getItems();
            itemsInCart.remove(product.get());
            found.get().getCart().setItems(itemsInCart);
        }
    }

}
