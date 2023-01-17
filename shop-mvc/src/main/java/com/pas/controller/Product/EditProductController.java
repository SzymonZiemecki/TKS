package com.pas.controller.Product;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.restClient.ProductApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@ConversationScoped
@Getter
@Setter
public class EditProductController implements Serializable {
    @Inject
    ProductApiClient productApiClient;
    @Inject
    UserApiClient userApiClient;

    Product currentProduct;
    String productType;

    List<Order> currentProductOrders;
    Map<UUID, String> currentUsers;
    String currentUserId;

    public String update() throws CloneNotSupportedException {
        productApiClient.updateProduct(currentProduct.getId(), currentProduct);
        return "ListAllProducts";
    }

    public String addToCart() {
        userApiClient.addToCart(UUID.fromString(currentUserId), currentProduct.getId(), 1l);
        return "ListAllProducts";
    }
}
