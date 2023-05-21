package com.tks.microservices.userservice.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tks.microservices.userservice.rest.dto.CreateClientDto;

@FeignClient("order-service")
public interface OrderApiClient {

    @PostMapping("/add")
    ResponseEntity create(@RequestBody CreateClientDto dto);
}
