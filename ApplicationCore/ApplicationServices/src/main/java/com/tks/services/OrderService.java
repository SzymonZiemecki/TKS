package com.tks.services;

import com.tks.infrastructure.orders.AddOrderPort;
import com.tks.infrastructure.orders.DeleteOrderPort;
import com.tks.infrastructure.orders.GetOrderPort;
import com.tks.infrastructure.orders.UpdateOrderPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {
    @Inject
    AddOrderPort addOrderPort;
    @Inject
    DeleteOrderPort deleteOrderPort;
    @Inject
    GetOrderPort getOrderPort;
    @Inject
    UpdateOrderPort updateOrderPort;

    public void essA(){
        System.out.println("ez");
    }
}
