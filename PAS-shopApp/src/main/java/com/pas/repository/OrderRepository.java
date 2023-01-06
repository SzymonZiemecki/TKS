package com.pas.repository;

import com.pas.model.Order;
import jakarta.enterprise.context.ApplicationScoped;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderRepository extends IRepositoryImpl<Order> {

    public List<Order> findOngoingOrders(){
        return filter(order -> !order.isDelivered());
    }

    public List<Order> findFinishedOrders(){
        return filter(Order::isDelivered);
    }

}
