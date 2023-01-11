package com.pas.controller.Order;

import com.pas.controller.Conversational;
import com.pas.manager.OrderManager;
import com.pas.manager.UserManager;
import com.pas.model.Order;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class OrdersController extends Conversational implements Serializable {
    @Inject
    OrderManager orderManager;
    @Inject
    UserManager userManager;
    User currentUser;
    List<Order> currentOrders;

    @PostConstruct
    public void initCurrentProducts(){
        currentOrders = orderManager.findAllOrders();
    }

    public String deliverOrder(Order order){
        orderManager.deliverOrder(order.getId());
        return "ListAllOrders";
    }
}
