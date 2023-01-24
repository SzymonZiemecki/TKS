package com.pas.controller.User;

import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.OrderApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
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

import static com.pas.controller.User.EditProfileController.context;

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
    UserDTO currentUser;

    Cart cart;

    List<Order> currentUserOrders;

    public String removeFromCart(Product product) {
        userApiClient.removeFromCart(currentUser.getId(), product.getId());
        currentUser = userApiClient.getUserById(currentUser.getId());
        return "UserCart";
    }

    public String createOrder(){
        orderApiClient.createOrder(currentUser.getId(), currentUser.getAddress());
        return "UserCart";
    }

    @PostConstruct()
    public void init(){
        currentUser = userApiClient.findOneByLogin(context().getUserPrincipal().getName()).get(0);
        cart = userApiClient.getUserCart(currentUser.getId());
    }
}
