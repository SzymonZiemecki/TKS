package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.Order;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.OrderApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.pas.controller.User.EditProfileController.context;

@Named
@ViewScoped
@Getter
@Setter
public class OrderUserListController extends Conversational implements Serializable {
    @Inject
    OrderApiClient orderApiClient;
    @Inject
    UserApiClient userApiClient;
    UserDTO currentUser;
    List<Order> currentOrders;

    @PostConstruct
    public void initCurrentOrders(){
        Response res = userApiClient.findOneByLogin(ChangePasswordController.context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
        currentOrders = userApiClient.getUserOrders(currentUser.getId());
    }
    public String deliverOrder(Order order){
        orderApiClient.deliverOrder(order.getId());
        return "ListAllOrders";
    }
}
