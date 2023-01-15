package com.pas.controller.User;

import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.OrderAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
@Getter
@Setter
public class UserCartController implements Serializable {
    UserAPI userAPI = ClientFactory.userAPIClient();
    OrderAPI orderAPI = ClientFactory.orderAPIClient();
    @Inject
    CommonUserController commonUserController;
    User currentUser;
    List<Order> currentUserOrders;

    public String removeFromCart(Product product) {
        userAPI.removeFromCart(currentUser.getId(), product.getId());
        currentUser = userAPI.getUserById(currentUser.getId());
        return "UserCart";
    }

    public String createOrder(){
        orderAPI.createOrder(currentUser.getId(), currentUser.getAddress());
        return "ListAllUsers";
    }
}
