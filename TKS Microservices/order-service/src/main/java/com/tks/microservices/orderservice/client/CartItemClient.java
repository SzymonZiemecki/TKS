package com.tks.microservices.orderservice.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tks.microservices.orderservice.core.model.Product;

@FeignClient(name = "product-service")
public interface CartItemClient {

    @GetMapping("/product/{id}")
    public Product findById(@PathVariable("id") UUID catItemId);
}
