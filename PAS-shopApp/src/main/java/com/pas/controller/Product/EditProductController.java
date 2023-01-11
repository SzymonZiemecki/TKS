package com.pas.controller.Product;

import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
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
    ProductManager productManager;
    @Inject
    UserManager userManager;
    @Inject
    CommonProductController commonProductController;

    Product currentProduct;
    String productType;

    List<Order> currentProductOrders;
    Map<UUID,String> currentUsers;
    String currentUserId;

    public String update() throws CloneNotSupportedException {
        productManager.updateProduct(currentProduct.getId(), currentProduct);
        return "ListAllProducts";
    }

    public String addToCart() {
        userManager.addToCart(UUID.fromString(currentUserId), currentProduct.getId(), 1l);
        return "ListAllProducts";
    }
}
