package com.pas.manager;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.User.CartItem;
import com.pas.model.User.User;
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
        if(!updatedUser.getId().equals(updatedUser.getId())){
            throw new IllegalArgumentException("Cant change id");
        }
        User user = findById(updatedUser.getId());
        user.setPassword(updatedUser.getPassword());
        user.setAccountBalance(updatedUser.getAccountBalance());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        return userRepository.update(id, updatedUser);
    }

    public void suspendOrResumeUser(UUID userId) {
        Optional<User> found = userRepository.findById(userId);
        if (found.isPresent()) {
            found.get().setSuspended(!found.get().isSuspended());
            userRepository.update(found.get().getId(), found.get());
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            found.get().getCart().getCartItems().add(new CartItem(product.get(), quantity));
            return found.get().getCart();
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    public Cart removeFromCart(UUID userId, UUID productId) {
        Optional<User> found = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            List<CartItem> itemsInCart = found.get().getCart().getCartItems();
            deleteItemFromCartItems(itemsInCart, product.get());
            found.get().setCart(new Cart(itemsInCart));
            return found.get().getCart();
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    private List<CartItem> deleteItemFromCartItems(List<CartItem> cartItems, Product product){
        List<CartItem> itemsToDelete = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct()
                .equals(product))
                .collect(Collectors.toList());
        List<CartItem> itemsLeft = cartItems;
        itemsToDelete.forEach( item -> itemsLeft.remove(item));
        return itemsLeft;
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
        user.setCart(new Cart(new ArrayList<>()));
        userRepository.update(user.getId(), user);
    }

    public List<Order> findAllUserOrders(UUID userId) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(userId));
    }

    public Cart getUserCart(UUID userId) {
        return findById(userId).getCart();
    }
}
