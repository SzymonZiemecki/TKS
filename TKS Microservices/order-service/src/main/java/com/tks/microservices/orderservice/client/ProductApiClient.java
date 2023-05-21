package com.tks.microservices.orderservice.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.orderservice.core.model.Product;

@FeignClient(name = "product-service")
public interface ProductApiClient {

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") UUID catItemId);

    @PutMapping("/{id}/amount")
    ResponseEntity updateProductAvailableAmount(@PathVariable("id") UUID id, @RequestParam("amount") Long amount);
}
