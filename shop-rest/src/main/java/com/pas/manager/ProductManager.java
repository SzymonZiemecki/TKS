//package com.pas.manager;
//
//import com.pas.exception.BusinessLogicException;
//import com.pas.model.Order;
//import com.pas.model.Product.Product;
//import com.pas.model.User.CartItem;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityNotFoundException;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;
//import static com.pas.utils.ErrorInfo.PRODUCT_IN_UNFINISHED_ORDER;
//
//@ApplicationScoped
//public class ProductManager {
//
//    @Inject
//    private ProductRepository productRepository;
//
//    @Inject
//    OrderRepository orderRepository;
//
//    public Product findById(UUID id) {
//        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
//    }
//
//    public List<Product> findAllProducts() {
//        return productRepository.findAll();
//    }
//
//    public List<Product> findByName(String name) {
//        return productRepository.findByName(name);
//    }
//
//    public List<Product> findByProducer(String producer) {
//        return productRepository.findByProducer(producer);
//    }
//
//    public Product addItem(Product product) {
//        return productRepository.add(product);
//    }
//
//    public Product updateProduct(UUID id, Product product) {
//        return productRepository.update(id, product);
//    }
//
//    public void removeItem(UUID id) {
//        if (!isInOngoingOrder(id)) {
//            productRepository.delete(id);
//        } else {
//            throw new BusinessLogicException(PRODUCT_IN_UNFINISHED_ORDER.getValue());
//        }
//    }
//
////TODO: szymoZa: do orderow
//    private boolean isInOngoingOrder(UUID productId) {
//        return orderRepository.filter(order -> !order.isDelivered()).stream()
//                .map(order -> order.getItems())
//                .flatMap(list -> list.stream())
//                .map(CartItem::getProduct)
//                .map(product -> product.equals(productId))
//                .filter($ -> $.equals(true))
//                .findAny()
//                .orElse(false);
//    }
//
//    public List<Product> getProducts(Optional<String> producer, Optional<String> name) {
//        if (producer.isPresent()) {
//            return findByProducer(producer.get());
//        } else if (name.isPresent()) {
//            return findByName(name.get());
//        } else {
//            return findAllProducts();
//        }
//    }
////TODO: szymoZa: do orderow
//    public List<Order> ordersContainingProduct(Product product){
//        return orderRepository.findAll().stream()
//                .filter(order -> order.getItems().stream()
//                .map(CartItem::getProduct).collect(Collectors.toList()).contains(product))
//                .collect(Collectors.toList());
//    }
//}
