//package com.pas.manager;
//
//import com.pas.model.Address;
//import com.pas.model.Order;
//import com.pas.model.User.CartItem;
//import com.pas.model.User.User;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityNotFoundException;
//import com.pas.exception.BusinessLogicException;
//import com.pas.model.Product.Product;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//
//import static com.pas.utils.ErrorInfo.*;
//
//
//@ApplicationScoped
//public class OrderManager {
//
//    @Inject
//    private OrderRepository orderRepository;
//
//    @Inject
//    private UserManager userManager;
//
//    @Inject
//    private ProductManager productManager;
//
//    public Order findById(UUID id) {
//        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
//    }
//
//    public List<Order> findAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    public Order createOrder(UUID userId, Address shippingAddress) {
//        User customer = userManager.findById(userId);
//        List<CartItem> orderItems = customer.getCart().getCartItems();
//        Double orderValue = calculateOrderValue(orderItems);
//
//        if (shouldCreateOrder(userId, orderItems, shippingAddress, orderValue)) {
//            process(customer, orderItems, orderValue, shippingAddress);
//            Order order = orderRepository.add(new Order(customer, shippingAddress, orderItems, new Date(), true, 0, false, calculateOrderValue(orderItems)));
//            userManager.clearUserCart(customer.getId());
//            return order;
//        } else {
//            throw new BusinessLogicException(ORDER_VIOLATED_BUSINESS_LOGIC.getValue());
//        }
//    }
//
//    private Map<Product, Long> toProductMap(Map<UUID, Long> orderItemsIds) {
//        return orderItemsIds.entrySet()
//                .stream()
//                .collect(Collectors.toMap( e -> productManager.findById(e.getKey()), Map.Entry::getValue));
//    }
//
//    private Map<UUID, Long> toIdMap(Map<Product, Long> orderItems) {
//        return orderItems.entrySet()
//                .stream()
//                .collect(Collectors.toMap( e -> e.getKey().getId(), Map.Entry::getValue));
//    }
//
//    public void cancelOrder(UUID id) {
//        orderRepository.delete(id);
//    }
//
//    public void deliverOrder(UUID orderId) {
//        Order found = findById(orderId);
//        found.setDelivered(true);
//        found.setDeliveryDate(new Date());
//        orderRepository.update(found.getId(), found);
//    }
//
//    private synchronized void process(User user, List<CartItem> products, Double orderValue, Address address) {
//        user.setAccountBalance(user.getAccountBalance() - orderValue);
//        products.forEach((product) -> product.getProduct().setAvailableAmount((int) (product.getProduct().getAvailableAmount() - product.getQuantity())));
//    }
//
//    private boolean shouldCreateOrder(UUID userId, List<CartItem> orderItems, Address shippingAddress, Double orderValue) {
//        if (isEnoughItems(orderItems) && !isUserSuspended(userId) && checkIfEnoughMoney(userId, orderValue)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isEnoughItems(List<CartItem> orderItems) {
//        return orderItems.stream()
//                .map((orderItem -> isEnoughItems(orderItem.getProduct(), (long) orderItem.getProduct().getAvailableAmount())))
//                .filter(result -> result.equals(true))
//                .findAny()
//                .orElseThrow(() -> new BusinessLogicException(ORDER_ITEM_OUT_OF_STOCK.getValue()));
//    }
//
//    private boolean isUserSuspended(UUID userId) {
//        User found = userManager.findById(userId);
//        return Optional.ofNullable(found)
//                .map(User::isSuspended)
//                .filter(result -> result.equals(false))
//                .orElseThrow(() -> new BusinessLogicException(ORDER_CUSTOMER_SUSPENDED.getValue()));
//    }
//
//    private boolean checkIfEnoughMoney(UUID userId, Double orderValue) {
//        User found = userManager.findById(userId);
//        return Optional.ofNullable(found)
//                .map(user -> isEnoughMoney(user.getAccountBalance(), orderValue))
//                .filter(result -> result.equals(true))
//                .orElseThrow(() -> new BusinessLogicException(ORDER_INSUFFICIENT_FUNDS.getValue()));
//    }
//
//    private boolean isEnoughItems(Product product, Long availableAmount) {
//        return product.getAvailableAmount() - availableAmount >= 0;
//    }
//
//    private boolean isEnoughMoney(Double userMoney, Double orderValue) {
//        return userMoney - orderValue >= 0;
//    }
//
//    private Double calculateOrderValue(List<CartItem> orderItems) {
//        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
//        orderItems.forEach((cartItem) -> orderValue.updateAndGet(value -> value + cartItem.getProduct().getPrice() * cartItem.getQuantity()));
//        return orderValue.get();
//    }
//
//    public void deleteOrder(UUID orderId) {
//        User user = findUserInOrder(orderId);
//        Order order = findById(orderId);
//        if(order.isDelivered()) {
//            orderRepository.delete(orderId);
//        } else {
//            throw new BusinessLogicException(ORDER_DELETE_ONGOING_ERROR.getValue());
//        }
//    }
//
//    public List<Order> findOngoingOrders() {
//        return orderRepository.findOngoingOrders();
//    }
//
//    public List<Order> findFinishedOrders() {
//        return orderRepository.findFinishedOrders();
//    }
//
//    public User findUserInOrder(UUID orderId){
//        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())).getCustomer();
//    }
//}
