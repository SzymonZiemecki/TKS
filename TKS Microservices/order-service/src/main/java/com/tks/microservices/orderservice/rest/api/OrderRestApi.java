package com.tks.microservices.orderservice.rest.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.orderservice.rest.dto.AddressDTO;
import com.tks.microservices.orderservice.rest.dto.CreateClientDto;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

public interface OrderRestApi {
    ResponseEntity getAllOrders();

    ResponseEntity getOrderById(@PathParam("id") UUID id);

    ResponseEntity getOngoingOrders();

    ResponseEntity getFinishedOrders();

    ResponseEntity createOrder(@QueryParam("userId") UUID userId, @Valid AddressDTO shippingAddress);

    ResponseEntity deliverOrder(@PathParam("id") UUID orderId);

    ResponseEntity deleteOrder(@PathParam("id") UUID orderId);

    ResponseEntity create(@RequestBody CreateClientDto dto);

    ResponseEntity addToCart(@PathParam("id") UUID orderId, @RequestParam("productId") UUID id);

    ResponseEntity removeFromCart(@PathParam("id") UUID orderId, @RequestParam("productId") UUID id);
}
