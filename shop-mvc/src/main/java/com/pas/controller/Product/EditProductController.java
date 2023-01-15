package com.pas.controller.Product;

import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.ProductAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Named
@ConversationScoped
@Getter
@Setter
public class EditProductController implements Serializable {
    ProductAPI productAPI = ClientFactory.productAPIClient();
    UserAPI userAPI = ClientFactory.userAPIClient();
    @Inject
    CommonProductController commonProductController;

    Product currentProduct;
    String productType;

    List<Order> currentProductOrders;
    Map<UUID,String> currentUsers;
    String currentUserId;

    public String update() throws CloneNotSupportedException {
        productAPI.updateProduct(currentProduct.getId(), currentProduct);
        return "ListAllProducts";
    }

    public String addToCart() {
        userAPI.addToCart(UUID.fromString(currentUserId), currentProduct.getId(), 1l);
        return "ListAllProducts";
    }
}
