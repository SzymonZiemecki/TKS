package com.tks.services;


import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.security.ProductRepositoryPort;
import com.tks.security.UserRepositoryPort;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;
import com.tks.userinterface.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Inject
    UserRepositoryPort userRepository;
    @Inject
    ProductRepositoryPort productRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByLogin(String login) {
        return userRepository.findAllByMatchingLogin(login);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Order> getOngoingUserOrders(UUID id) {
        return userRepository.findOngoingUserOrders(id);
    }

    @Override
    public List<Order> getFinishedUserOrders(UUID id) {
        return userRepository.findOngoingUserOrders(id);
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        return userRepository.update(id, updatedUser);
    }

    @Override
    public void changeUserPassword(UUID id, String currentPassword, String newPassword) {
        User user = getUserById(id);
        if (user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            userRepository.update(id, user);
        }
    }

    @SneakyThrows
    @Override
    public Cart getUserCart(UUID id) {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    @Override
    public List<Order> getAllUserOrders(UUID userId) {
        return userRepository.findUserOrders(userId);
    }

    @Override
    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        User user = getUserById(userId);
        Product product = productRepository.findById(productId);
        List<CartItem> itemsInCart = user.getCart().getCartItems();
        itemsInCart.add(new CartItem(product, quantity));
        user.setCart(new Cart(itemsInCart));
        return updateUser(userId, user).getCart();
    }

    @Override
    public Cart removeFromCart(UUID userId, UUID productId) {
        User user = getUserById(userId);
        Product product = productRepository.findById(productId);
        List<CartItem> itemsInCart = user.getCart().getCartItems();
        deleteItemFromCartItems(itemsInCart, product);
        user.setCart(new Cart(itemsInCart));
        return updateUser(userId, user).getCart();
    }

    @Override
    public User addUser(User user) {
        return userRepository.add(user);
    }

    @Override
    public void suspendOrResumeUser(UUID userId) {
        User user = getUserById(userId);
        user.setSuspended(!user.isSuspended());
        userRepository.update(userId, user);
    }

    private List<CartItem> deleteItemFromCartItems(List<CartItem> cartItems, Product product) {
        List<CartItem> itemsToDelete = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct()
                        .equals(product))
                .toList();
        List<CartItem> itemsLeft = cartItems;
        itemsToDelete.forEach(itemsLeft::remove);
        return itemsLeft;
    }
}
