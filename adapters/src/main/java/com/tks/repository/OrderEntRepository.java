package com.tks.repository;

import java.util.List;

import com.tks.api.OrderRepository;
import com.tks.data.model.OrderEnt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderEntRepository extends RepositoryImpl<OrderEnt> implements OrderRepository {

    public List<OrderEnt> findOngoingOrders(){
        return filter(order -> !order.isDelivered());
    }

    public List<OrderEnt> findFinishedOrders(){
        return filter(OrderEnt::isDelivered);
    }

}
