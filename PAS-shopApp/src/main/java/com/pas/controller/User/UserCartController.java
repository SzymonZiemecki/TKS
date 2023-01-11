package com.pas.controller.User;

import com.pas.manager.OrderManager;
import com.pas.manager.UserManager;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
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

@Named
@ConversationScoped
@Getter
@Setter
public class UserCartController implements Serializable {
    @Inject
    UserManager userManager;
    @Inject
    OrderManager orderManager;
    @Inject
    CommonUserController commonUserController;
    User currentUser;
    List<Order> currentUserOrders;

    public String removeFromCart(Product product) {
        userManager.removeFromCart(currentUser.getId(), product.getId());
        currentUser = userManager.findById(currentUser.getId());
        return "UserCart";
    }

    public String createOrder(){
        orderManager.createOrder(currentUser.getId(), currentUser.getAddress());
        return "ListAllUsers";
    }
}
