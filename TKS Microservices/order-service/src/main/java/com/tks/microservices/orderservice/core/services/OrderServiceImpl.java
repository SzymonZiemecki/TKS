package com.tks.microservices.orderservice.core.services;

import static java.util.stream.Collectors.toMap;
import static com.tks.microservices.orderservice.core.services.utils.Assertions.assertTrue;
import static com.tks.microservices.orderservice.repository.model.utils.ErrorInfoEnt.ORDER_DELETE_ONGOING_ERROR;
import static com.tks.microservices.orderservice.repository.model.utils.ErrorInfoEnt.ORDER_INSUFFICIENT_FUNDS;
import static com.tks.microservices.orderservice.repository.model.utils.ErrorInfoEnt.ORDER_ITEM_OUT_OF_STOCK;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tks.microservices.orderservice.client.ProductApiClient;
import com.tks.microservices.orderservice.core.model.Address;
import com.tks.microservices.orderservice.core.model.Cart;
import com.tks.microservices.orderservice.core.model.CartItem;
import com.tks.microservices.orderservice.core.model.Client;
import com.tks.microservices.orderservice.core.model.Order;
import com.tks.microservices.orderservice.core.model.Product;
import com.tks.microservices.orderservice.core.model.exception.BusinessLogicException;
import com.tks.microservices.orderservice.port.security.ClientRepositoryPort;
import com.tks.microservices.orderservice.port.security.OrderRepositoryPort;
import com.tks.microservices.orderservice.port.ui.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepositoryPort orderRepository;

    private ClientRepositoryPort clientRepository;

    private ProductApiClient productApiClient;

    public OrderServiceImpl(final OrderRepositoryPort orderRepository, final ClientRepositoryPort clientRepository,
                            final ProductApiClient productApiClient) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productApiClient = productApiClient;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id);
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
    public Order createOrder(UUID userId, Address shippingAddress) {
        Client client = clientRepository.findById(userId);
        List<CartItem> orderItems = client.getCart().getCartItems();

        Map<Product, Long> products = orderItems.stream()
                .collect(Collectors.toMap(
                        orderItem -> productApiClient.findById(orderItem.getProductId()), CartItem::getQuantity));

        Double orderValue = calculateOrderValue(products);

        assertCanCreateOrder(client, products, orderValue);
        process(client, products, orderValue);
        Order order = orderRepository.add(new Order(client, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(products)));
        clearUserCart(client);
        return order;
    }

    private void assertCanCreateOrder(Client client, Map<Product, Long> orderItems, Double orderValue) {
        assertTrue(this::isEnoughItems, orderItems, new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
        isEnoughItems(orderItems);
        checkIfEnoughMoney(client, orderValue);
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

    private Double calculateOrderValue(Map<Product, Long> orderItems) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        orderItems.forEach((cartItem, quantity) -> orderValue.updateAndGet(value -> value + cartItem.getPrice() * quantity));
        return orderValue.get();
    }

    private Boolean isEnoughItems(Map<Product, Long> orderItems) {
        return orderItems.keySet()
                .stream()
                .map(aLong -> isEnoughItemz(aLong, aLong.getAvailableAmount()))
                .allMatch(result -> result.equals(true));
    }

    private Boolean isEnoughItemz(Product product, Long availableAmount) {
        return product.getAvailableAmount() - availableAmount >= 0;
    }

    private void checkIfEnoughMoney(Client user, Double orderValue) {
        BiPredicate<Client, Double> isEnoughMoney = (u, v) -> u.getAccountBalance() - v >= 0;
        assertTrue(isEnoughMoney, user, orderValue, new BusinessLogicException(ORDER_INSUFFICIENT_FUNDS.getValue()));
    }

    private synchronized void process(Client user, Map<Product, Long> products, Double orderValue) {
        user.setAccountBalance(user.getAccountBalance() - orderValue);
        products.forEach((product, quantity) -> product.setAvailableAmount((product.getAvailableAmount() - quantity)));
        products.forEach((product, quantity) -> productApiClient.updateProductAvailableAmount(product.getId(), product.getAvailableAmount()));
    }

    private void clearUserCart(Client client) {
        client.setCart(new Cart(new ArrayList<>()));
        clientRepository.update(client.getId(), client);
    }
}
