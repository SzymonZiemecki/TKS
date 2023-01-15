package com.pas.controller.Order;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.OrderAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.Order;
import com.pas.model.User.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class OrdersController extends Conversational implements Serializable {
    OrderAPI orderAPI = ClientFactory.orderAPIClient();
    UserAPI userAPI = ClientFactory.userAPIClient();
    User currentUser;
    List<Order> currentOrders;

    @PostConstruct
    public void initCurrentProducts(){
        currentOrders = orderAPI.getAllOrders();
    }

    public String deliverOrder(Order order){
        orderAPI.deliverOrder(order.getId());
        return "ListAllOrders";
    }
}
