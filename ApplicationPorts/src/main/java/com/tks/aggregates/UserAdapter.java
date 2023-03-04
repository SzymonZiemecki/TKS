package com.tks.aggregates;

import com.tks.Product.Product;
import com.tks.User.User;
import com.tks.exceptions.PasswordMismatchExcpetion;
import com.tks.infrastructure.users.*;
import com.tks.mapper.ModelMapperBean;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;
import data.model.OrderEnt;
import data.product.ProductEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import repository.OrderEntRepository;
import repository.ProductEntRepository;
import repository.UserEntRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static data.utils.ErrorInfoEnt.ENTITY_NOT_FOUND_MESSAGE;
import static data.utils.ErrorInfoEnt.PASSWORD_MISMATCH;

@ApplicationScoped
public class UserAdapter implements AddUserPort, GetUserPort, UpdateUserPort {

    @Inject
    private UserEntRepository userRepository;

    @Inject
    private ProductEntRepository productRepository;

    @Inject
    private OrderEntRepository orderRepository;

    @Override
    public User createUser(User user) {
        return ModelMapperBean.toDomainModel(userRepository.add(ModelMapperBean.toEntModel(user)));
    }
    @Override
    public User findUserById(UUID userId) {
        return ModelMapperBean.toDomainModel(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())));
    }

    @Override
    public List<User> findAllUsers() {
        return ModelMapperBean.toDomainModel(userRepository.findAll());
    }

    @Override
    public List<Order> findUserOrders(UUID userId) {
        return ModelMapperBean.toDomainModel(userRepository.findUserOrders(userId));
    }

    @Override
    public List<Order> findOngoingUserOrders(UUID userId) {
        return ModelMapperBean.toDomainModel(orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(order -> !order.isDelivered())
                .collect(Collectors.toList()));
    }

    @Override
    public List<Order> findFinishedUserOrders(UUID userId) {
        return ModelMapperBean.toDomainModel(orderRepository.filter(order -> order.getCustomer().getId().equals(userId))
                .stream()
                .filter(OrderEnt::isDelivered)
                .collect(Collectors.toList()));
    }

    @Override
    public User findOneByLogin(String login) {
        return ModelMapperBean.toDomainModel(userRepository.findOneByLogin(login));
    }

    @Override
    public List<User> findAllByMatchingLogin(String login) {
        return userRepository.findByLogin(login).stream()
                .map(ModelMapperBean::toDomainModel)
                .map(obj -> (User) obj)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(UUID userId, User updatedUser) {
        User user = findUserById(updatedUser.getId());
        User updated = ModelMapperBean.toDomainModel(updatedUser);
        updated.setPassword(user.getPassword());
        return ModelMapperBean.toDomainModel(userRepository.update(userId, ModelMapperBean.toEntModel(updated)));
    }
}
