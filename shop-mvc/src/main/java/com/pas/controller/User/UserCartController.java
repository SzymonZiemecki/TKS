package com.pas.controller.User;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.restClient.OrderApiClient;
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

@Named
@ConversationScoped
@Getter
@Setter
public class UserCartController implements Serializable {
    @Inject
    UserApiClient userApiClient;
    @Inject
    OrderApiClient orderApiClient;
    @Inject
    CommonUserController commonUserController;
    User currentUser;
    List<Order> currentUserOrders;

    public String removeFromCart(Product product) {
        userApiClient.removeFromCart(currentUser.getId(), product.getId());
        currentUser = userApiClient.getUserById(currentUser.getId());
        return "UserCart";
    }

    public String createOrder(){
        orderApiClient.createOrder(currentUser.getId(), currentUser.getAddress());
        return "ListAllUsers";
    }
}
