package com.pas.repository;

import com.pas.model.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class OrderRepository extends IRepositoryImpl<Order> {
}
