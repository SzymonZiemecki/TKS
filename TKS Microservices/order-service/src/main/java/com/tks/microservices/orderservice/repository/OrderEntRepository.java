package com.tks.microservices.orderservice.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tks.microservices.orderservice.repository.api.OrderRepository;
import com.tks.microservices.orderservice.repository.model.OrderEnt;

@Component
public class OrderEntRepository extends RepositoryImpl<OrderEnt> implements OrderRepository {

    public List<OrderEnt> findOngoingOrders(){
        return filter(order -> !order.isDelivered());
    }

    public List<OrderEnt> findFinishedOrders(){
        return filter(OrderEnt::isDelivered);
    }

}
